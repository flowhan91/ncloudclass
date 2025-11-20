package com.lgdx.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication : 해당 클래스의 위치 기준으로 하위에 있는 파일을 읽어
//Spring Boot의 설정을 자동적으로 진행하는 클래스 이다. 

//@ComponentScan : @Controller @RestController @Servies @Repository 등을 연결해서 사용 가능하다. 

@SpringBootApplication
@MapperScan("com.lgdx.mapper")
public class SpringBoot2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot2Application.class, args);
	}
}
