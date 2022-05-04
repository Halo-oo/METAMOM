package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.MemberParam;

@Mapper
@Repository
public interface MemberDao {

	public int getId(MemberDto dto);	
	public int addmember(MemberDto dto);
	
	public MemberDto login(MemberDto dto);
	
	/* #21# Web용 _관리자 login */
	public MemberDto loginWeb(MemberDto dto);
	
	/* #21# ID 중복체크 */
	public int idCheck(String id);
	
	/* #21# 회원정보 수정 */
	public int userUpdate(MemberDto dto);
	
	//아이디찾기
	public String searchId(MemberDto dto);
	
	//비밀번호 찾기
	public String searchPwd(MemberDto dto);
	
	/* #21# (Web_관리자용) 검색 + 페이징 + 회원목록 & 회원목록 총 개수 */
	public List<MemberDto> getMemberListSearchPage(MemberParam param);
	
	public int getMemberCount(MemberParam param);
	
	/* #21# (Web_관리자용) 회원 탈퇴처리 */
	public int userDelWeb(String id);
	
	/* #21# (Web_관리자용) 회원 복구처리 */
	public int userRecoveryWeb(String id);
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(1)[오늘의식단] 식단기록 DB 내 삭제 */
	public int userDelRememberMealsWeb(MemberDto dto);
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(2)[SNS] SNS DB 내 삭제 */
	public int userDelWebSnsWeb(MemberDto dto);
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(3)[나의식단] 식단기록 DB 내 삭제 */
	public int userDelFoodListMealsWeb(MemberDto dto);
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(4)[구독] 구독 DB 내 삭제 */
	public int userDelSubscribeWeb(MemberDto dto);
	
	/* #21# (Web _관리자용) 회원 완전삭제 _(5)[회원] 회원 DB 내 삭제 */
	public int userDelMemberWeb(MemberDto dto);
	
	
}




