package mul.camp.a.dto;

public class RecipeDto {

	private int recipeSeq;
	private String recipeName;
	private String ingredient;
	private String brief;
	private String recipe;
	private String foodImage;
	private String recipeImage;
	private String kcal;
	
	public RecipeDto() {
		// TODO Auto-generated constructor stub
	}

	public RecipeDto(int recipeSeq, String recipeName, String ingredient, String brief, String recipe, String foodImage,
			String recipeImage, String kcal) {
		super();
		this.recipeSeq = recipeSeq;
		this.recipeName = recipeName;
		this.ingredient = ingredient;
		this.brief = brief;
		this.recipe = recipe;
		this.foodImage = foodImage;
		this.recipeImage = recipeImage;
		this.kcal = kcal;
	}

	public int getRecipeSeq() {
		return recipeSeq;
	}

	public void setRecipeSeq(int recipeSeq) {
		this.recipeSeq = recipeSeq;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getRecipe() {
		return recipe;
	}

	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}

	public String getFoodImage() {
		return foodImage;
	}

	public void setFoodImage(String foodImage) {
		this.foodImage = foodImage;
	}

	public String getRecipeImage() {
		return recipeImage;
	}

	public void setRecipeImage(String recipeImage) {
		this.recipeImage = recipeImage;
	}

	public String getKcal() {
		return kcal;
	}

	public void setKcal(String kcal) {
		this.kcal = kcal;
	}

	@Override
	public String toString() {
		return "RecipeDto [recipeSeq=" + recipeSeq + ", recipeName=" + recipeName + ", ingredient=" + ingredient
				+ ", brief=" + brief + ", recipe=" + recipe + ", foodImage=" + foodImage + ", recipeImage="
				+ recipeImage + ", kcal=" + kcal + "]";
	}

	
	
	
	
}
