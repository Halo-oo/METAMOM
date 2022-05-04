package mul.camp.a.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.SnsDao;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.MemberParam;
import mul.camp.a.dto.SnsCommentDto;
import mul.camp.a.dto.SnsDto;
import mul.camp.a.dto.SnsLikeDto;
import mul.camp.a.dto.SnsParam;

@Service
@Transactional
public class SnsService {
	
	@Autowired
	SnsDao dao;
	
	public int snsInsert(SnsDto dto) {
		System.out.println("service 실행");
		return dao.snsInsert(dto);
	}
	
	public int snsDelete(int seq) {
		System.out.println("snsDelete 실행");
		return dao.snsDelete(seq);
	}
	
	public int snsUpdate(SnsDto dto) {
		System.out.println("snsUpdate service 실행");
		return dao.snsUpdate(dto);
	}
	
	public int snsWebUpdate(SnsDto dto) {
		System.out.println("snsWebUpdate service 실행");
		return dao.snsWebUpdate(dto);
	}
	
	public SnsDto snsSearch(int seq) {
		System.out.println("snsSearch service 실행");
		return dao.snsSearch(seq);
	}
	
	public int snsImgUpdate(SnsDto dto) {
		System.out.println("snsImgUpdate service 실행");
		return dao.snsImgUpdate(dto);
	}
	
	public int snsCommentInsert(SnsCommentDto dto) {
		System.out.println("snsCommentInsert service 실행");
		return dao.snsCommentInsert(dto);
	}
	
	public MemberDto snsGetMmeber(String id) {
		System.out.println("snsGetMmeber service 실행");
		return dao.snsGetMmeber(id);
	}
	
	public ArrayList<SnsDto> allSns(){
		System.out.println("allSns service 실행");
		return dao.allSns();
	}
	
	public ArrayList<SnsCommentDto> allComment(int seq){
		System.out.println("allComment service 실행");
		return dao.allComment(seq);
	}
	
	public int snsLikeInsert(SnsLikeDto dto) {
		System.out.println("snsLikeInsert service 실행");
		return dao.snsLikeInsert(dto);
	}
	
	public int snsLikeDelete(SnsLikeDto dto) {
		System.out.println("snsLikeDelete service 실행");
		return dao.snsLikeDelete(dto);
	}
	
	public int snsLikeAllDelete(int seq) {
		System.out.println("snsLikeAllDelete service 실행");
		return dao.snsLikeAllDelete(seq);
	}
	
	public int snsLikeCheck(SnsLikeDto dto) {
		System.out.println("snsLikeCheck service 실행");
		return dao.snsLikeCheck(dto);
	}
	
	public int snsLikeCount(int seq) {
		System.out.println("snsLikeCount service 실행");
		return dao.snsLikeCount(seq);
	}
	public int snsCommentCount(int seq) {
		System.out.println("snsCommentCount service 실행");
		return dao.snsCommentCount(seq);
	}
	
	public int snsCommentAllDelete(int seq) {
		System.out.println("snsCommentAllDelete service 실행");
		return dao.snsCommentAllDelete(seq);
	}
	
	public int snsCommentDelete(int cmtseq) {
		System.out.println("snsCommentDelete service 실행");
		return dao.snsCommentDelete(cmtseq);
	}
	
	public int snsCommentUpdate(SnsCommentDto dto) {
		System.out.println("snsCommentUpdate service 실행");
		return dao.snsCommentUpdate(dto);
	}
	
	public int nextSeq() {
		System.out.println("nextSeq service 실행");
		return dao.nextSeq();
	}
	
	public int currSeq() {
		System.out.println("currSeq service 실행");
		return dao.currSeq();
	}
	/* #21# (Web_관리자용) 검색 + 페이징 + 회원목록 & 회원목록 총 개수 */
	public List<SnsDto> getSnsListSearchPage(MemberParam param) {
		return dao.getSnsListSearchPage(param);
	}
	public int getSnsCount(MemberParam param) {
		return dao.getSnsCount(param);
	}
}
