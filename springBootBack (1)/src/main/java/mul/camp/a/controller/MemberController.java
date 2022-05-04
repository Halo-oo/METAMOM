package mul.camp.a.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.tools.javac.util.List;

import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.MemberParam;
import mul.camp.a.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	MemberService service;
	
	@RequestMapping(value = "/getId", method = {RequestMethod.GET, RequestMethod.POST} )
	public String getId(MemberDto dto) {
		System.out.println("MemberController getId");
		System.out.println("HelloHelloHelloHelloHelloHello");
		boolean b = service.getId(dto);
		if(b) {
			return "NO";
		}
		return "OK";
	}
	
	@RequestMapping(value = "/addmember", method = {RequestMethod.GET, RequestMethod.POST} )
	public String addmember(@RequestBody MemberDto dto) {
		System.out.println("MemberController addmember");
		System.out.println(dto.toString());
		
		boolean b = service.addmember(dto);
		if(b) {
			return "YES";
		}
		return "NO";
	}
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST} )
	public MemberDto login(@RequestBody MemberDto dto, HttpServletRequest req) {
		System.out.println("MemberController login");
		
		MemberDto mem = service.login(dto);
		
		// 이렇게도 사용할 수 있다
		req.getSession().setAttribute("login", mem);
		
		return mem;
	}
	
	/* #21# Web용 _관리자 login */
	@RequestMapping(value = "/loginWeb", method = RequestMethod.POST)
	public MemberDto loginWeb(MemberDto dto, HttpServletRequest req) {
		System.out.println("#21# MemberController Web용 관리자 login()");
		
		MemberDto mem = service.loginWeb(dto);
		
		// 이렇게도 사용할 수 있다
		req.getSession().setAttribute("loginWeb", mem);
		
		return mem;
	}
	
	/* #21# ID 중복체크 */
	@RequestMapping(value = "/idCheck", method = {RequestMethod.GET, RequestMethod.POST} )
	public boolean idCheck(String id) {
		System.out.println("#21# MemberController idCheck ID 중복체크 동작");
		System.out.println("#21# ID 중복체크를 위하여 받아온 id값: " + id);
		
		// service.idCheck가 true == 중복 ID가 존재, false == 중복 ID 없음
		return service.idCheck(id);
	}
	
	/* #21# 회원정보 수정 */
	@RequestMapping(value = "/userUpdate", method = {RequestMethod.GET, RequestMethod.POST} )
	public boolean userUpdate(@RequestBody MemberDto dto) {
		System.out.println("#21# MemberController userUpdate() 회원정보 수정 동작");
		System.out.println("#21# 회원정보 수정을 위하여 받아온 dto값: " + dto);
		
		// service.userUpdate가 true == 회원정보 수정 성공, false == 회원정보 수정 실패
		return service.userUpdate(dto);
	}
	
	/* #21# (Web _관리자용) 회원정보 수정 */
	@RequestMapping(value = "/userUpdateWeb", method = {RequestMethod.GET, RequestMethod.POST} )
	public String userUpdateWeb(MemberDto dto) {
		System.out.println("#21# MemberController userUpdateWeb() 관리자용 _회원정보 수정 동작");
		System.out.println("#21# (관리자용) _회원정보 수정을 위하여 받아온 dto값: " + dto);
		
		// service.userUpdate가 true == 회원정보 수정 성공, false == 회원정보 수정 실패
		Boolean result = service.userUpdate(dto);
		
		if (result) {
			return "YES";
		}
		else {
			return "FAIL";
		}
	}
	
	//아이디찾기
	@RequestMapping(value="/searchId",method = {RequestMethod.POST})
	public String searchId(@RequestBody MemberDto dto) {
		
		System.out.println("MemberController searchId");
		System.out.println(dto.toString());
		
		String id = service.searchId(dto);
		System.out.println("id:" + id);
		/*
		 * if(id == null || id.equals("")) { System.out.println("잘못됨"); id = "";
		 * System.out.println("id:" + id); return id; } else { System.out.println("잘됨");
		 * System.out.println("id:" + id); return id; }
		 */
		return id;
	}
	//비밀번호 찾기
	@RequestMapping(value="/searchPwd",method = {RequestMethod.POST})
	public String searchPwd(@RequestBody MemberDto dto) {
		
		System.out.println("MemberController searchPwd");
		System.out.println(dto.toString());
		
		String pwd = service.searchPwd(dto);
		System.out.println(pwd);
	
		return pwd;
	}
	
	/* #21# (Web용 _관리자) 회원목록 & 회원목록의 총 개수 */
	@RequestMapping(value = "/getMemberListSearchPage", method = {RequestMethod.GET, RequestMethod.POST} )	// 검색조건에 맞는 글의 개수
	public java.util.List<MemberDto> getMemberListSearchPage(MemberParam param) {
		System.out.println("#21# MemberController getMemberListSearchPage() 동작");
		System.out.println("#21# (관리자용) _for 회원목록을 위하여 받아온 값 param: " + param);
		
		// 페이지 설정 
		int sn = param.getPage();	// .getPage(): 현재 페이지 ex. 0 1 2 3 ~ 
		int start = sn * 10 + 1;	// ex) sn = 0 >> (start)1 ~ (end)10		sn = 1 >> (start)11 ~ (end)20
		int end = (sn + 1) * 10;
		
		param.setStart(start);
		param.setEnd(end);
		
		return service.getMemberListSearchPage(param);
	}
	/* #21# (Web용 _관리자) 회원목록의 총 개수 (위의 회원목록과 동시 동작) */
	@RequestMapping(value = "/getMemberCount", method = RequestMethod.POST)	// 회원목록의 총 개수
	public int getMemberCount(MemberParam param) {
		System.out.println("#21# MemberController getMemberCount() 동작");
		
		System.out.println("#21# getMemberCount() 결과값 > " + service.getMemberCount(param));
		return service.getMemberCount(param);
	}
	
	/* #21# (Web _관리자용) 회원 탈퇴 */
	@RequestMapping(value = "/userDelWeb", method = RequestMethod.POST)
	public boolean userDelWeb(String id) {
		System.out.println("#21# MemberController userDelWeb() 관리자용 _회원 탈퇴처리 동작");
		System.out.println("#21# (관리자용) _회원정보 탈퇴을 위하여 받아온 id값: " + id);
		
		// service.userUpdate가 true == 회원 탈퇴처리 성공, false == 회원 탈퇴처리 실패
		return service.userDelWeb(id);
	}
	
	/* #21# (Web _관리자용) 회원 복구 */
	@RequestMapping(value = "/userRecoveryWeb", method = RequestMethod.POST)
	public boolean userRecoveryWeb(String id) {
		System.out.println("#21# MemberController userRecoveryWeb() 관리자용 _회원 복구처리 동작");
		System.out.println("#21# (관리자용) _회원정보 복구를 위하여 받아온 id값: " + id);
		
		// service.userUpdate가 true == 회원 복구처리 성공, false == 회원 복구처리 실패
		return service.userRecoveryWeb(id);
	}
	
	/* #21# (Web _관리자용) 회원 완전 삭제 */
	@RequestMapping(value = "/memberDbDelWeb", method = RequestMethod.POST)
	public boolean memberDbDelWeb(MemberDto dto) {
		System.out.println("#21# MemberController memberDbDelWeb() 관리자용 _회원 완전삭제 동작");
		System.out.println("#21# (관리자용) _회원 완전삭제를 위하여 받아온 dto값: " + dto);
		
		boolean successCheck = true;
		
		// 1) [오늘의식단] 식단기록 DB 내 삭제
		// - 삭제 성공 시 true 
		if (dto.getSubscribe() == 1) {
			System.out.println("#21# [오늘의식단] 식단기록 DB 내 삭제 동작");
			
			successCheck = service.userDelRememberMealsWeb(dto);
			System.out.println("#21# [오늘의식단] 식단기록 DB 내 삭제 동작결과 > " + successCheck);
			if (successCheck == false) System.out.println("#21# userDelRememberMealsWeb() 오늘의식단 기록 DB 내 회원데이터 삭제 실패");
		}
		
		// 2) [SNS] SNS DB 내 삭제
		if (successCheck == true) {
			System.out.println("#21# [SNS] SNS DB 내 삭제 동작");
			
			successCheck = service.userDelWebSnsWeb(dto);
			System.out.println("#21# [SNS] SNS DB 내 삭제 동작결과 > " + successCheck);
			if (successCheck == false) System.out.println("#21# userDelWebSnsWeb() SNS DB 내 회원데이터 삭제 실패");
		}
		
		// 3) [나의식단] 식단기록 DB 내 삭제
		if (successCheck == true) {
			System.out.println("#21# [나의식단] 식단기록 DB 내 삭제 동작");
			
			successCheck = service.userDelFoodListMealsWeb(dto);
			System.out.println("#21# [나의식단] 식단기록 DB 내 삭제 동작결과 > " + successCheck);
			if (successCheck == false) System.out.println("#21# userDelFoodListMealsWeb() 나의식단 식단기록 DB 내 회원데이터 삭제 실패");
		}
		
		// 4) [구독] 구독 DB 내 삭제
		if (dto.getSubscribe()==1 && successCheck == true) {
			System.out.println("#21# [구독] 구독 DB 내 삭제 동작");
			
			successCheck = service.userDelSubscribeWeb(dto);
			System.out.println("#21# [구독] 구독 DB 내 삭제 동작결과 > " + successCheck);
			if (successCheck == false) System.out.println("#21# userDelSubscribeWeb() 구독회원 DB 내 회원데이터 삭제 실패");
		}
		
		// 5) [회원] 회원 DB 내 삭제
		if (successCheck == true) {
			System.out.println("#21# [회원] 회원 DB 내 삭제 동작");
			
			successCheck = service.userDelMemberWeb(dto);
			System.out.println("#21# [회원] 회원 DB 내 삭제 동작결과 > " + successCheck);
			if (successCheck == false) System.out.println("#21# userDelMemberWeb() 회원 DB 내 회원데이터 삭제 실패");
		}
		
		return successCheck;
	}
	
}












