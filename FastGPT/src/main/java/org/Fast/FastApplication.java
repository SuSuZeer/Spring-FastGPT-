package org.Fast;

import org.Fast.utils.EmailUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/***
 * @title FastApplication
 * @author SUZE
 * @Date 16:23
 **/
@MapperScan("org.Fast.service")
@MapperScan("org.Fast.mapper.UserMapper")
@EnableScheduling
@SpringBootApplication
public class FastApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(FastApplication.class, args);
    }
}
