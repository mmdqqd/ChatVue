package org.example.vuetest2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.example.vuetest2.common.Result;
import org.example.vuetest2.entity.Movie;
import org.example.vuetest2.service.MovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/page")
    public Result<Page<Movie>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "6") Integer size,  // 默认每页6条
            @RequestParam(required = false) String movieName) {
        // 创建分页对象
        Page<Movie> page = new Page<>(current, size);
        // 创建条件构造器
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        // 如果有搜索条件，添加like查询
        if (movieName != null && !movieName.isEmpty()) {
            queryWrapper.like("movie_name", movieName);
        }
        // 添加排序
        queryWrapper.orderByAsc("id");
        // 执行分页查询
        Page<Movie> result = movieService.page(page, queryWrapper);
        return Result.success(result);
    }


} 