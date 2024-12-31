package org.example.vuetest2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.vuetest2.entity.Movie;
import org.example.vuetest2.mapper.MovieMapper;
import org.example.vuetest2.service.MovieService;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {
} 