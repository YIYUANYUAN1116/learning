package com.xht.es.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.GeoShapeRelation;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.xht.common.vo.Result;
import com.xht.common.vo.ResultCodeEnum;
import com.xht.es.entity.Account;
import com.xht.es.entity.CasePerson;
import com.xht.es.entity.Hospital;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.SearchTemplateQuery;
import org.springframework.data.elasticsearch.core.query.SearchTemplateQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author : YIYUANYUAN
 * @date: 2024/10/4  16:00
 */
@RestController
@RequestMapping("/yq")
@Tag(name = "疫情地图接口")
public class YQController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @GetMapping("/list")
    @Operation(summary = "查询全部")
    public Result getAll() {
        SearchResponse<CasePerson> search = null;
        try {
            search = elasticsearchClient.search(sr ->
                    sr.query(q -> q.matchAll(qma -> qma)), CasePerson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.build(search.hits().hits().toString(), ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/add")
    @Operation(summary = "查询某天的新增数，2022-06-29")
    public Result getAdd(@RequestParam String date, @RequestParam(required = false) Integer size) {
        SearchResponse<CasePerson> search = null;
        try {
            //确诊状态
            Query status = TermQuery.of(t -> t
                    .field("status.keyword")
                    .value("确诊"))._toQuery();
            //日期
            Query dateQ = RangeQuery.of(r -> r.
                    field("date")
                    .gte(JsonData.of(date)
                    )
            )._toQuery();

            search = elasticsearchClient.search(sr -> sr
                    .query(q -> q
                            .bool(b -> b
                                    .must(status)
                                    .must(dateQ)
                            )
                    )
                    .size(size), CasePerson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.build(search.hits().hits().toString(), ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/rangCase")
    @Operation(summary = "查询周边确诊")
    public Result rangCase(@RequestParam double lat, @RequestParam double lon) {
        SearchResponse<CasePerson> search = null;
        try {
            GeoDistanceQuery distanceQuery = GeoDistanceQuery.of(g -> g
                    .distance("3km")
                    .field("location")
                    .location(l -> l
                            .latlon(la -> la
                                    .lat(lat)
                                    .lon(lon))));
            search = elasticsearchClient.search(sr -> sr
                            .query(q -> q
                                    .geoDistance(distanceQuery)
                            )
                            .index("case_person")
                    , CasePerson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.build(search.hits().hits().toString(), ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/rangHospital")
    @Operation(summary = "查询周边医院")
    public Result rangHospital(@RequestParam double lat, @RequestParam double lon) {
        SearchResponse<Hospital> search = null;
        try {
            GeoDistanceQuery distanceQuery = GeoDistanceQuery.of(g -> g
                    .distance("3km")
                    .field("location")
                    .location(l -> l
                            .latlon(la -> la
                                    .lat(lat)
                                    .lon(lon)
                            )
                    )
            );
            search = elasticsearchClient.search(sr -> sr
                            .query(q -> q
                                    .geoDistance(distanceQuery)
                            )
                            .index("hospital")
                    , Hospital.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.build(search.hits().hits().toString(), ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/areaHospital")
    @Operation(summary = "搜索某个区域附近的的医院")
    public Result rangHospital(@RequestParam String areaId) {
        SearchResponse<Hospital> search = null;
        try {
            GeoShapeQuery geoShapeQuery = GeoShapeQuery.of(ge -> ge
                    .field("location")
                    .shape(s -> s
                            .indexedShape(is -> is
                                    .index("province_bak")
                                    .path("location")
                                    .id(areaId))
                            .relation(GeoShapeRelation.Within)
                    )
            );
            search = elasticsearchClient.search(sr -> sr
                            .query(q -> q
                                    .geoShape(geoShapeQuery)
                            )
                            .index("hospital")
                    , Hospital.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.build(search.hits().hits().toString(), ResultCodeEnum.SUCCESS);
    }
}
