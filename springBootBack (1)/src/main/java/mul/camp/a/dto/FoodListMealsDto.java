package mul.camp.a.dto;

public class FoodListMealsDto {
	private String id;
	private int seqfoodlist;
	private String wdate;
	private String meals;
	private String memo;
	private String imgUrl;
	private String foodscore;
	private int del;
	
 	public FoodListMealsDto(){
	}

	public FoodListMealsDto(String id, int seqfoodlist, String wdate, String meals, String memo, String imgUrl,
			String foodscore, int del) {
		super();
		this.id = id;
		this.seqfoodlist = seqfoodlist;
		this.wdate = wdate;
		this.meals = meals;
		this.memo = memo;
		this.imgUrl = imgUrl;
		this.foodscore = foodscore;
		this.del = del;
	}
	
	

	public FoodListMealsDto(int seqfoodlist, String meals, String memo, String imgUrl, String foodscore) {
		super();
		this.seqfoodlist = seqfoodlist;
		this.meals = meals;
		this.memo = memo;
		this.imgUrl = imgUrl;
		this.foodscore = foodscore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeqfoodlist() {
		return seqfoodlist;
	}

	public void setSeqfoodlist(int seqfoodlist) {
		this.seqfoodlist = seqfoodlist;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getMeals() {
		return meals;
	}

	public void setMeals(String meals) {
		this.meals = meals;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getFoodscore() {
		return foodscore;
	}

	public void setFoodscore(String foodscore) {
		this.foodscore = foodscore;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return "FoodListMealsDto [id=" + id + ", seqfoodlist=" + seqfoodlist + ", wdate=" + wdate + ", meals=" + meals
				+ ", memo=" + memo + ", imgUrl=" + imgUrl + ", foodscore=" + foodscore + ", del=" + del + "]";
	}
 	
}

	