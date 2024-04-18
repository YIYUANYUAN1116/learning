package com.xht.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xht.common.api.PageParamDto;
import com.xht.common.utils.ApprenticeUtil;
import com.xht.common.vo.Result;
import com.xht.common.vo.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 核心公共controller类
 */
public class BaseController<S extends IService<E>, E> {

    @Autowired
    protected S baseService;

    @Operation(summary ="增")
    @PostMapping
    public Result<Object> insert(@RequestBody E entity) {
        baseService.save(entity);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary ="删")
    @DeleteMapping
    public Result<Object> delete(@RequestBody List<Integer> ids) {
        baseService.removeByIds(ids);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary ="改")
    @PatchMapping
    public Result<Object> updateById(@RequestBody E entity) {
        baseService.updateById(entity);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary ="查")
    @GetMapping
    public Result<Object> getById(@RequestParam Integer id) {
        return Result.build(baseService.getById(id), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary ="存")
    @PutMapping
    public Result<Object> save(@RequestBody E entity) {
        baseService.saveOrUpdate(entity);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary ="list查")
    @GetMapping("/list")
    public Result<Object> list(E entity) {
        QueryWrapper<E> queryWrapper = ApprenticeUtil.getQueryWrapper(entity);
        List<E> list = baseService.list(queryWrapper);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary ="page查")
    @GetMapping("/page")
    public Result<Object> page(@RequestBody PageParamDto<E> pageParamDto) {
        //限制条件
        if (pageParamDto.getPageNum() < 1) {
            pageParamDto.setPageNum(1);
        }

        if (pageParamDto.getPageSize() > 100) {
            pageParamDto.setPageSize(100);
        }
        Page<E> page = new Page<>(pageParamDto.getPageNum(), pageParamDto.getPageSize());
        QueryWrapper<E> queryWrapper = ApprenticeUtil.getQueryWrapper(pageParamDto.getParam());
        //升序
        String asc = pageParamDto.getAsc();
        if (StringUtils.isNotEmpty(asc) && !"null".equals(asc)) {
            String[] split = asc.split(",");
            queryWrapper.orderByAsc(Arrays.asList(split));
        }
        //降序
        String desc = pageParamDto.getDesc();
        if (StringUtils.isNotEmpty(desc) && !"null".equals(desc)) {
            String[] split = desc.split(",");
            queryWrapper.orderByDesc(Arrays.asList(split));
        }
        Page<E> ePage = baseService.page(page, queryWrapper);
        return Result.build(ePage, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary ="获取数量")
    @GetMapping("/count")
    public Result<Object> count(@RequestBody E entity) {
        QueryWrapper<E> queryWrapper = ApprenticeUtil.getQueryWrapper(entity);
        long count = baseService.count(queryWrapper);
        return Result.build(count, ResultCodeEnum.SUCCESS);
    }

}
