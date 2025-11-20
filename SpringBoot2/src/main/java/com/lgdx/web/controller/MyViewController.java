package com.lgdx.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Spring Boot 에서는 FC가 Controller 를 찾는 작업을 자동으로 진행된다. 
//SpringBoot1Application.java에서 

@Controller
public class MyViewController {
	
	@GetMapping("/")
	public String goHome() {
		
		return "Main";
	}
	
	@GetMapping("/goUpdate")
	public String goUpdate() {
		return "UpdateMember";
	}
	
	
	
}
