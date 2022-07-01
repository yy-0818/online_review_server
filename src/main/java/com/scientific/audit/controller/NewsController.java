package com.scientific.audit.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scientific.audit.common.model.base.Result;
import com.scientific.audit.common.model.entity.News;
import com.scientific.audit.mapper.NewsMapper;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


@RestController
@RequestMapping("/news")
@Api(tags = "资源接口")
public class NewsController{

    @Resource
    NewsMapper newsMapper;


    @PostMapping
    @Operation(summary = "保存")
    public Result save(@RequestBody News news) {
        news.setTime(new Date());
        newsMapper.insert(news);
        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "根据ID更改")
    public Result update(@RequestBody News news) {
        newsMapper.updateById(news);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除")
    public Result delete(@PathVariable Long id) {
        newsMapper.deleteById(id);

        return Result.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取")
    public Result getById(@PathVariable Long id) {
        return Result.ok(newsMapper.selectById(id));
    }

    @GetMapping
    @Operation(summary = "分页查询")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(News::getTitle, search);
        }
        Page<News> newsPage = newsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.ok(newsPage);
    }


}
