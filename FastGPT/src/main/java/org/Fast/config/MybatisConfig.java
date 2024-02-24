package org.Fast.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import org.Fast.mapper.JSONArrayTypeHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
/*/**
 *
 *@author suze
 *@date 2023-10-25
 *@time 15:13
 *
 **/


//@MapperScan("TopOne.UserSystem.entity")\
@MapperScan("org.Fast.mapper")
//@MapperScan("TopOne.entity")
@Configuration
public class MybatisConfig {

    @Autowired
    private JSONArrayTypeHandler jsonArrayTypeHandler;
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public DataSource dataSource() {
        // 配置您的数据源，这里使用 DriverManagerDataSource 作为示例
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/fast?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("791616");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        // 设置 MyBatis 配置文件的位置
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("mybatis-config.xml"));
        return sessionFactory.getObject();
    }

    @Bean
    public JSONArrayTypeHandler jsonArrayTypeHandler() {
        return new JSONArrayTypeHandler();
    }
}
