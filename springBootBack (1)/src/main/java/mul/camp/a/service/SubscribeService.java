package mul.camp.a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.SubscribeDao;
import mul.camp.a.dto.SubDietMealDto;
import mul.camp.a.dto.SubExerMealDto;
import mul.camp.a.dto.SubMealRememberDto;
import mul.camp.a.dto.SubscribeDto;

@Service
@Transactional
public class SubscribeService {
	
	@Autowired
	SubscribeDao dao;
	
	/* #21# 구독 회원정보 가져오기 */
	public SubscribeDto getSubInfo(String id) {
		//System.out.println("#21# Dao 받은 id값 : " + id);
		return dao.getSubInfo(id);
	}

	
	/* #21# 구독 회원추가 + 멤버 구독값 수정 */
	// 1) 구독 추가 (구독 만료일자 제외)
	public boolean subAdd(SubscribeDto dto) {
		int result = dao.subAdd(dto);
		
		return result>0?true:false;
	}
	// 1-1) 구독 만료일자 추가
	public void subAddEndday(SubscribeDto dto) {
		dao.subAddEndday(dto);
	}
	// 2) 멤버 TABLE 내 구독값 수정
	public void subUpdateMember(SubscribeDto dto) {
		dao.subUpdateMember(dto);
	}
	
	
	/* #21# 구독 만료확인 */
	// 1) 구독 만료확인
	public SubscribeDto subEnddayCheck(SubscribeDto dto) {
		return dao.subEnddayCheck(dto);
	}
	// 2) 멤버TABLE 내 구독값 수정 
	public void subUpdateMemberEnd(SubscribeDto dto) {
		dao.subUpdateMemberEnd(dto);
	}
	// 2-1) 구독TABLE 내 사용자 삭제
	public boolean subDeleteUser(SubscribeDto dto) {
		int result = dao.subDeleteUser(dto);
		
		return result>0?true:false;
	}
	
	
	/* #21# 오늘의 다이어트 식단 RANDOM SELECT (1개) */
	// 1) 다이어트
	public SubDietMealDto subRandomDietMeal(SubDietMealDto dto) {
		return dao.subRandomDietMeal(dto);
	}
	// 2) 운동
	public SubExerMealDto subRandomExerMeal(SubExerMealDto dto) {
		return dao.subRandomExerMeal(dto);
	}
	
	
	/* #21# 추천한 오늘의 식단 저장(추가) */
	public boolean subMealRemember(SubMealRememberDto dto) {
		int result = dao.subMealRemember(dto);
		
		return result>0?true:false;
	}
	
	
	/* #21# 오늘의 식단을 추천 이력 판별 */
	public SubMealRememberDto subLogCheckMeal(SubMealRememberDto dto) {
		return dao.subLogCheckMeal(dto);
	}
	
	/* #21# 추천하였던 *[다이어트]* 식단 가져오기 */
	public SubDietMealDto subDietMeal(int subDietSeq) {
		return dao.subDietMeal(subDietSeq);
	}
	
	/* #21# 추천하였던 *[다이어트]* 식단 가져오기 */
	public SubExerMealDto subExerMeal(int subExerSeq) {
		return dao.subExerMeal(subExerSeq);
	}
	
	
	/* #21# 추천하였던 식단 중 3일 이상인 식단 삭제 */
	public int subRememberDel(String subDelRemId) {
		//System.out.println("#21# Service _#Front에서 가져온 삭제할 식단의 ID값: " + subDelRemId);
		return dao.subRememberDel(subDelRemId);
	}
}
