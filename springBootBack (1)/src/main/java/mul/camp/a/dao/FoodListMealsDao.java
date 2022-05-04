package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.FoodListMealsDto;
import mul.camp.a.dto.idWdateDto;

@Mapper
@Repository
public interface FoodListMealsDao {

	public int writeFoodList(FoodListMealsDto dto); // 나의식단 쓰기
	
	public List<FoodListMealsDto> writeFoodSelect(String id); // 나의식단 리스트 부르기
	
	public String checkId(String id); // 아이디 하나 매칭 추출

	public List<FoodListMealsDto> writeFoodSelectDay(idWdateDto dto); // 날짜 선택시 매칭 리스트 추출

	public int deleteFoodList(int seqfoodlist);

	public int updateFoodlist(FoodListMealsDto dto);

	//public List<FoodListMealsDto> writeFoodSelect(int seqfoodlist);// 디테일 seqfoodlist 일치 정보 출력
}
