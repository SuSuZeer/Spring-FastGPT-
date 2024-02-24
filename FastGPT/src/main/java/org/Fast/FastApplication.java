package org.Fast;

import org.Fast.utils.EmailUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/***
 * @title FastApplication
 * @author SUZE
 * @Date 16:23
 **/
@MapperScan("org.Fast.service")
@MapperScan("org.Fast.mapper.UserMapper")
@SpringBootApplication
public class FastApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(FastApplication.class, args);
    }
}
