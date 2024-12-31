package org.example.vuetest2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.vuetest2.mapper")
public class Vuetest2Application {

    public static void main(String[] args) {
        SpringApplication.run(Vuetest2Application.class, args);
    }

}
