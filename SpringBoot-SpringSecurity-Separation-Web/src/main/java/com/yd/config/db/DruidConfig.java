package com.yd.config.db;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 配置druid需要的配置类，引入application.properties文件中以spring.datasource开头的信息
 * 因此需要在application.properties文件中配置相关信息。
 * 只是将DataSource对象的实现类变为了DruidDataSource对象
 * @author Administrator
 *
 */
@Configuration
public class DruidConfig {
	//如果项目刚启动，未执行sql，直接去druid/index.html查看数据源，会报数据源不存在
    @Bean(initMethod="init")
    @ConfigurationProperties(prefix = "spring.datasource")  
    public DataSource druidDataSource() {  
        DruidDataSource druidDataSource = new DruidDataSource();  
        return druidDataSource;  
    } 
}