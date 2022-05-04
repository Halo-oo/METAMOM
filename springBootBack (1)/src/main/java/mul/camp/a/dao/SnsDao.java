package mul.camp.a.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.MemberParam;
import mul.camp.a.dto.SnsCommentDto;
import mul.camp.a.dto.SnsDto;
import mul.camp.a.dto.SnsLikeDto;
import mul.camp.a.dto.SnsParam;

@Mapper
@Repository
public interface SnsDao {
	public int snsInsert(SnsDto dto);
	public int snsDelete(int seq);
	public int snsUpdate(SnsDto dto);
	public int snsWebUpdate(SnsDto dto);
	public SnsDto snsSearch(int seq);
	public int snsImgUpdate(SnsDto dto);
	public MemberDto snsGetMmeber(String id);
	public ArrayList<SnsDto> allSns();
	public ArrayList<SnsCommentDto> allComment(int seq);
	public int snsLikeInsert(SnsLikeDto dto);
	public int snsLikeDelete(SnsLikeDto dto);
	public int snsLikeAllDelete(int seq);
	public int snsLikeCheck(SnsLikeDto dto);
	public int snsLikeCount(int seq);
	public int snsCommentCount(int seq);
	public int snsCommentInsert(SnsCommentDto dto);
	public int snsCommentAllDelete(int seq);
	public int snsCommentDelete(int cmtseq);
	public int snsCommentUpdate(SnsCommentDto dto);
	public int nextSeq();
	public int currSeq();
	/* #21# (Web_관리자용) 검색 + 페이징 + 회원목록 & 회원목록 총 개수 */
	public List<SnsDto> getSnsListSearchPage(MemberParam param);
	public int getSnsCount(MemberParam param);
}
