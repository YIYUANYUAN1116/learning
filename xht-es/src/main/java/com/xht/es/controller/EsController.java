package com.xht.es.controller;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.xht.common.vo.Result;
import com.xht.common.vo.ResultCodeEnum;


import com.xht.es.entity.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.AggregationsContainer;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : YIYUANYUAN
 * @date: 2024/1/21  17:25
 */

@RestController
@RequestMapping("/learn/es")
@Tag(name = "es 测试接口")
public class EsController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchClient elasticsearchClient;


    @GetMapping("/list/{page}/{size}")
    @Operation(summary = "查询全部")
    public Result getAll(@PathVariable Integer page,
                         @PathVariable Integer size){
//        PageRequest of = PageRequest.of(page, size);
//
//        NativeQuery build = new NativeQueryBuilder().withQuery(Query.findAll()).withPageable(of).build();
//
//        SearchHits<Account> search = elasticsearchTemplate.search(build, Account.class);
        SearchResponse<Account> search = null;
        try {
            search = elasticsearchClient.search(sr ->
                    sr.query(q -> q.matchAll(qma->qma)), Account.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.build(search.hits().hits().toString(), ResultCodeEnum.SUCCESS);
    }


    @GetMapping("/{id}")
    @Operation(summary = "按id查询")
    public Result getById(@PathVariable Integer id){
        GetResponse<Account> response = null;
        try {
            response = elasticsearchClient.get(g -> g
                            .index("account")
                            .id(String.valueOf(id)),
                    Account.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Result.build(response.source(), ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/addr/{addr}")
    @Operation(summary = "按地址查询")
    public Result getByAddress(@PathVariable String addr){
        SearchResponse<Account> search = null;
        try {
            search = elasticsearchClient.search(sr -> sr
                            .index("account")
                            .query(q -> q
                                    .match(m -> m
                                            .field("address")
                                            .query(addr)))
                    , Account.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Account> accounts = new ArrayList<>();
        for (Hit<Account> hit : search.hits().hits()) {
            accounts.add(hit.source());
        }
        return Result.build(accounts, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/test/1")
    @Operation(summary = "按照年龄聚合，并且请求这些年龄段的这些人的平均薪资")
    public Result test1(){

//        SearchResponse<Account> search = null;
//        try {
//            search = elasticsearchClient.search(sr -> sr
//                            .size(0)
//                            .aggregations("age_agg", a -> a.terms(t -> t.field("age"))
//                                    .aggregations("avg_balance",av->av.avg(v->v.field("balance"))))
//                    , Account.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        List<LongTermsBucket> ageAgg = search.aggregations().get("age_agg").lterms().buckets().array();

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(q -> q.matchAll(qma -> qma))
                .withAggregation("age_agg", Aggregation.of(aa -> aa.terms(ageAggT -> ageAggT.field("age"))
                        .aggregations("avg_balance", Aggregation.of(ab -> ab.avg(abt -> abt.field("balance"))))
                )).build();


        SearchHits<Account> search = elasticsearchTemplate.search(query, Account.class);


        return Result.build(search.getAggregations().aggregations().toString(),ResultCodeEnum.SUCCESS);

    }


    @GetMapping("/test/2")
    @Operation(summary = "查出所有年龄分布，并且这些年龄段中 M 的平均薪资和 F 的平均薪资以及这个年龄段的总体平均薪资")
    public Result test2(){

        SearchResponse<Account> search = null;
        try {

            search = elasticsearchClient.search(sr -> sr
                            .size(0)
                            .aggregations("age_agg", aa -> aa.terms(aat -> aat.field("age"))
                                    .aggregations("avg_balance", ab -> ab.avg(abt -> abt.field("balance")))
                                            .aggregations("gender_agg",ga->ga.terms(gat->gat.field("gender.keyword"))
                                                    .aggregations("gender_balance_avg",gba->gba.avg(gbat->gbat.field("balance"))))
                                    )

                    , Account.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        List<LongTermsBucket> ageAgg = search.aggregations().get("age_agg").lterms().buckets().array();
        return Result.build(ageAgg.toString(), ResultCodeEnum.SUCCESS);
    }

}
