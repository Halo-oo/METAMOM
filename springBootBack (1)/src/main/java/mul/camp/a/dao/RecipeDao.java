package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.RecipeDto;

@Mapper
@Repository
public interface RecipeDao {

	public List<RecipeDto> getRecipe(RecipeDto dto);
	public List<RecipeDto> getRecipeWeb(RecipeDto dto);
	
	public int writeRecipe(RecipeDto dto);
	
}
