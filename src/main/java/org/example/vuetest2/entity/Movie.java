package org.example.vuetest2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Movie {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String movieName;
    private String movieIntroduce;
    private Double movieStar;
    private String movieEvaluate;
} 