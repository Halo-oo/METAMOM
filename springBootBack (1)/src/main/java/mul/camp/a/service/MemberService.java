package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.MemberDao;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.MemberParam;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	MemberDao dao;
	
	public boolean getId(MemberDto dto) {
		int n = dao.getId(dto);
	
		return n>0?true:false;
	}
	
	public boolean addmember(MemberDto dto) {
		int n = dao.addmember(dto);
		System.out.println(dto.toString());
		return n>0?true:false;
	}
	
	public MemberDto login(MemberDto dto) {
		
		return dao.login(dto);		
	}
	
	/* #21# Web용 _관리자 login */
	public MemberDto loginWeb(MemberDto dto) {
		return dao.loginWeb(dto);
	}
	
	/* #21# ID 중복체크 */
	// true면 ID 중복이 존재하는 것
	public boolean idCheck(String id) {
		int result = dao.idCheck(id);
		
		return result>0?true:false;
	}
	
	/* #21# 회원정보 수정 */
	public boolean userUpdate(MemberDto dto) {
		int result = dao.userUpdate(dto);
		
		return result>0?true:false;
	}
	
	public String searchId(MemberDto dto) {
		
		return dao.searchId(dto);
		
	}
	public String searchPwd(MemberDto dto) {
		return dao.searchPwd(dto);
	}
	
	/* #21# (Web_관리자용) 검색 + 페이징 + 회원목록 & 회원목록 총 개수 */
	public List<MemberDto> getMemberListSearchPage(MemberParam param) {
		return dao.getMemberListSearchPage(param);
	}
	public int getMemberCount(MemberParam param) {
		return dao.getMemberCount(param);
	}
	
	/* #21# (Web_관리자용) 회원 탈퇴처리 */
	public boolean userDelWeb(String id) {
		int result = dao.userDelWeb(id);
		
		return result>0?true:false;
	}
	
	/* #21# (Web_관리자용) 회원 복구처리 */
	public boolean userRecoveryWeb(String id) {
		int result = dao.userRecoveryWeb(id);
		
		return result>0?true:false;
	}
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(1)[오늘의식단] 식단기록 DB 내 삭제 */
	public boolean userDelRememberMealsWeb(MemberDto dto) {
		int result = dao.userDelRememberMealsWeb(dto);
		//System.out.println("#21# MemberService userDelRememberMealsWeb() 실행결과 result값 > " + result);
		
		if (result == 0 || result > 0) return true;
		else return false;
		//return dao.userDelRememberMealsWeb(dto)>=0?true:false;
	}
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(2)[SNS] SNS DB 내 삭제 */
	public boolean userDelWebSnsWeb(MemberDto dto) {
		int result = dao.userDelWebSnsWeb(dto);
		
		if (result == 0 || result > 0) return true;
		else return false;
	}
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(3)[나의식단] 식단기록 DB 내 삭제 */
	public boolean userDelFoodListMealsWeb(MemberDto dto) {
		int result = dao.userDelFoodListMealsWeb(dto);
		
		if (result == 0 || result > 0) return true;
		else return false;
	}
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(4)[구독] 구독 DB 내 삭제 */
	public boolean userDelSubscribeWeb(MemberDto dto) {
		int result = dao.userDelSubscribeWeb(dto);
		
		if (result == 0 || result > 0) return true;
		else return false;
	}
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(5)[회원] 회원 DB 내 삭제 */
	public boolean userDelMemberWeb(MemberDto dto) {
		int result = dao.userDelMemberWeb(dto);
		
		if (result == 0 || result > 0) return true;
		else return false;
	}

}







