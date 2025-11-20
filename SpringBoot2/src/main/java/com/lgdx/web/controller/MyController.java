package com.lgdx.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgdx.mapper.MsgMemberMapper;
import com.lgdx.web.entity.MsgMember;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyController {
	// DB 연결해서 데이터를 조회하거나 생성하는 기능을 처리하는 컨트롤러

	// 데이터를 확인하는 용도로 console 출력할 도구 만들기
	private Logger logger = LoggerFactory.getLogger(getClass());

	// Spring Container가 해당하는 인터페이스 구현체를 직접 생성해서 주입
	@Autowired
	private MsgMemberMapper mapper;
	@PostMapping("join")
	public String join(MsgMember mem, Model model) {
		//1. 데이터 수집
		//내가 해당하는 데이터를 하나로 표현하는 자료형 : MsgMember.
		//MsgMember 생성, 요청 데이터를 매개변수로 수집. 
		logger.info("수집한 데이터 확인>>"+mem);
		
		//2. DB에 연결해서 데이터 추가
		mapper.join(mem);
		//모델 영역에 회원가입한 email값만 담아서 다음 페이지로 넘겨주기
		model.addAttribute("email",mem.getEmail());
		
		return "JoinSuccess"; //회원가입 완료시 JoinSuccess.jsp 페이지이름 반환.
		
		
	}
	
	// 로그인 작업
	// 1.들어온 요청을 판단(매핑)할수있는작업
	@PostMapping("/login")
	public String login(MsgMember mem, HttpSession session) {
		
		//로그인의 기능은 DB 에서 회원의 정보가 체크된다면 해당 회원에 대한 정보를 담아서 이동할 수 있다. 
		MsgMember member = mapper.login(mem);
		logger.info("로그인 된 회원의 정보>>"+member.toString());
		
		//모든 페이지 기능에서 사용할 수 있는 Session Scope 영역 사용 (model 무겁게 )
		//session을 매개변수로 받아와주어야 메소드 안에서 사용 가능하다. 
		session.setAttribute("member",member); //세션에 모든 멤버에 대한 정보(필드)를 저장
		
		
		return "Main";
	}
	
	//Get 요청 (로그아웃)
	// 요청 키워드 : logout
	//결과화면: Main
	//Session 에 지정된 member 데이터 삭제
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("member");
		return "Main";
	}
	
	
	//회원정보 업데이트 기능을 위한 요청 진행하기
	//1. 요청 매핑 진행
	//2.응답 구조 : form 태그를 통해 입력받은 데이터를 활용
	//3. DB에 해당 데이터를 전달 -> update sql 구문 실행
	//이메일이 일치할때 pw,tel,address가 수정되도록
	//4. 결과 화면은 main으로 이동. 
	@PostMapping("/update")
	public String update(MsgMember mem, HttpSession session) {
		
		MsgMember mem2 = (MsgMember) session.getAttribute("member"); //기존에 있던 email을 넘기기 위해 세션에 등록된 멤버를 가지고 온다.
		mem.setEmail(mem2.getEmail()); //기존에 있던 멤버의 이메일을 새롭게 지정한다. 
		
		mapper.update(mem);
		
		//세션 업데이트
		session.setAttribute("member", mem);
		
		return "Main";
	}
	
	@GetMapping("/showMember")
	public String showMember(Model model) { // 해당 페이지에서만 보여주면 되기 때문에 가벼운 모델을 사용한다. 
		List<MsgMember> list = mapper.showMember(); // list 형태로 모든 회원에 대한 정보를 가져왔다. 
		model.addAttribute("list",list); //모델에게 데이터 넘겨주기.
		
		return "ShowMember";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("email") String email) {
		
		mapper.deleteMember(email); //DB 접근
		
		//redirect: 클라이언트에게 새로운 url로 다시 요청을 보내도록 지시할때 사용
		return "redirect:showMember";
	}
	
	
	
	
	
	
}