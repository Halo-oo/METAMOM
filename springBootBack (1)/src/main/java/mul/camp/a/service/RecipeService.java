package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.RecipeDao;
import mul.camp.a.dto.RecipeDto;

@Service
@Transactional
public class RecipeService {

	@Autowired
	RecipeDao dao;
	
	public List<RecipeDto> getRecipe(RecipeDto dto) {
		
		System.out.println("나오냐ㅜ ");
		
		
		List<RecipeDto> list = dao.getRecipe(dto);
		System.out.println("RecipeDto"+list);
		
		return list;			
	}	
	
	public List<RecipeDto> getRecipeWeb(RecipeDto dto) {
		
		System.out.println("나오냐ㅜ ");		
	
		List<RecipeDto> list = dao.getRecipeWeb(dto);
		System.out.println("RecipeDto"+list);
		
		return list;				
	}
	
	
	public boolean writeRecipe(RecipeDto dto) {
		int n = dao.writeRecipe(dto);
		
		return n>0?true:false;
	}
	
}
