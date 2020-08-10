package com.tcl.sql;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/5 17:00
 */
@Configuration
public class MyDataSourceConfig {

    @Autowired
    DataSourceProperties properties;

/*    @Primary
    @Bean
    public DataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
 */


    @Bean
    @Primary
    public DataSource dataSource() {
        return properties.initializeDataSourceBuilder().build();
    }

    @ConfigurationProperties("spring.datasource.quartz")
    @QuartzDataSource
    @Bean
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }

}
