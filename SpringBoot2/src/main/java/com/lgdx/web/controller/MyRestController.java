package com.lgdx.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgdx.mapper.MsgMemberMapper;
import com.lgdx.web.entity.MsgMember;

@RestController
public class MyRestController {
	
	// 동기방식의 요청처리 -> 일반 Controller로 작업.
	//비동기 방식의 요청 처리를 할때 RestController로 작업한다. 
	@Autowired
	private MsgMemberMapper mapper;
	
	@GetMapping("/checkEmail")
	public String checkEmail(@RequestParam("inputEmail") String email) {
		MsgMember result = mapper.selectByEmail(email);
		if(result==null) {
			//확인되는 회원이 없음 => 사용가능한 이메일
			return "true";
		}else {
			//이미 사용중인 email
			return "false";
		}
	}
}
