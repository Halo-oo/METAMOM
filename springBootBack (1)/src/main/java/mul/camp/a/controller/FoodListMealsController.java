package mul.camp.a.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mul.camp.a.dto.FoodListMealsDto;
import mul.camp.a.dto.idWdateDto;
import mul.camp.a.service.FoodListMealsService;

@RestController
public class FoodListMealsController {
	@Autowired
	FoodListMealsService service;
	
	@Autowired
	ServletContext servletContext;
	
	//리스트 입력
	@RequestMapping(value = "/FoodListTest" , method = {RequestMethod.GET, RequestMethod.POST})
	public String FoodListTest(@RequestBody FoodListMealsDto dto) {
		System.out.println("FoodMealsListController test");
		System.out.println(dto.toString());
		
		boolean a = service.writeFoodList(dto);
		if(a) {
			return "잘넘어옴";
		}else return "안넘어옴";
	}
	// 아이디 일치 리스트
	@RequestMapping(value="/FoodListSelect", method = {RequestMethod.GET})
	public List<FoodListMealsDto> writeFoodSelect(String id){
		System.out.println("FoodMealsListController FoodListSelect " );
		List<FoodListMealsDto> list = service.writeFoodSelect(id);
		System.out.println(list.toString());
		return list;
	}
	// 날짜 아이디 일치 리스트
	@RequestMapping(value="/FoodListSelectDay", method = {RequestMethod.GET})
	public List<FoodListMealsDto> writeFoodSelectDay(idWdateDto dto){
		System.out.println("FoodMealsListController FoodListSelectDay " );
		System.out.println("id:" + dto.getId());
		System.out.println("wdate:" + dto.getWdate());
		List<FoodListMealsDto> list = service.writeFoodSelectDay(dto);
		System.out.println(list.toString());
		return list;
	}	
	
	//삭제
	@RequestMapping(value="/deleteFoodList",method = {RequestMethod.POST})
	public String deleteFoodList(@RequestBody int seqfoodlist) {
		System.out.println("FoodMealsListController deleteFoodList ");
		System.out.println("seqfoodlist:" + seqfoodlist);
		boolean b = service.deleteFoodList(seqfoodlist);
		if(b) {
			return "완료";
		}
		return "에러";
	}
	
	//업데이트,
		@RequestMapping(value="/updateFoodList",method = {RequestMethod.GET, RequestMethod.POST})
		public String updateFoodList(@RequestBody FoodListMealsDto dto) {
			System.out.println("!!!!!!!!!!!!!!!!! FoodMealsListController updateFoodList ");
			System.out.println("seqfoodlist:" + dto);
			boolean b = service.updateFoodlist(dto);
			if(b) {
				return "완료";
			}
			return "에러";
		}
		
		/*
		 * //디테일 seq 일치 리스트 불러오기
		 * 
		 * @RequestMapping(value="/detailSelect", method = {RequestMethod.GET}) public
		 * List<FoodListMealsDto> detailSelect(int seqfoodlist){
		 * System.out.println("FoodMealsListController detailSelect " );
		 * List<FoodListMealsDto> list = service.writeFoodSelect(seqfoodlist);
		 * 
		 * return list; }
		 */
	
	
	@RequestMapping(value="/checkId",method = {RequestMethod.GET})
	public String checkId(String id) {
		System.out.println("FoodMealsListController checkId");
		System.out.println("id:" + id);
		String a = service.checkId(id);
		if(a != null)return "넘어옴";
		else return "안넘어옴";
	}
}
















