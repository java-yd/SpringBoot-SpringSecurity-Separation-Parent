package com.yd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@ServletComponentScan //配置druid必须加的注解，如果不加，访问页面打不开，filter和servlet、listener之类的需要单独进行注册才能使用，spring boot里面提供了该注解起到注册作用
@MapperScan("com.yd.mapper")//必须加这个，不加报错，如果不加，也可以在每个mapper上添加@Mapper注释，
public class WebApp {

	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
		System.out.println("webApp  start....");
	}
}
