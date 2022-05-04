package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.FoodListMealsDao;
import mul.camp.a.dto.FoodListMealsDto;
import mul.camp.a.dto.MemberDto;
import mul.camp.a.dto.idWdateDto;

@Service
@Transactional
public class FoodListMealsService {
	@Autowired
	FoodListMealsDao dao;
	//식단 list 입력 
	public boolean writeFoodList(FoodListMealsDto dto) {
		int n = dao.writeFoodList(dto);
		return n>0?true:false;
	}
	//식단
	public List<FoodListMealsDto> writeFoodSelect(String id) {
		return dao.writeFoodSelect(id);
	}
	//아이디 하나 추출
	public String checkId(String id) {
		String n = dao.checkId(id);
		return n;
	}
	//달력 날짜 눌렀을경우 날짜 매칭
	public List<FoodListMealsDto> writeFoodSelectDay(idWdateDto dto) {
			return dao.writeFoodSelectDay(dto);
	}
	//삭제
	public boolean deleteFoodList(int seqfoodlist) {
		int n = dao.deleteFoodList(seqfoodlist);
		return n>0?true:false;
	}
	//업데이트
	public boolean updateFoodlist(FoodListMealsDto dto) {
		int n = dao.updateFoodlist(dto);
		return n>0?true:false;
	}
	// 디테일 seqfoolist 일치 select
	//public List<FoodListMealsDto> writeFoodSelect(int seqfoodlist) {
		
		//return dao.writeFoodSelect(seqfoodlist);
	//}	
}
