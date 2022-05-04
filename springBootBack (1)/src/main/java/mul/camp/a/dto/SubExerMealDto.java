package mul.camp.a.dto;

/* #21# 오늘의 [운동] 식단에 사용하는 Dto */
public class SubExerMealDto {
	
	private int subefSeq;
	private int subefTime;
	private String subefImage;
	private String subefName;
	private double subefKcal;
	//private int subefAmount;		// (04.05) 음식 양 제외
	private String subefType;
	private String subefID;			// 현재 로그인한 사용자 id, 중복되지 않는 식단 추천을 위하여 사용 
	
	public SubExerMealDto() {
		
	}

	public SubExerMealDto(int subefSeq, int subefTime, String subefImage, String subefName,
			double subefKcal/* , int subefAmount */, String subefType, String subefID) {
		super();
		this.subefSeq = subefSeq;
		this.subefTime = subefTime;
		this.subefImage = subefImage;
		this.subefName = subefName;
		this.subefKcal = subefKcal;
		//this.subefAmount = subefAmount;
		this.subefType = subefType;
		this.subefID = subefID;
	}

	public int getSubefSeq() {
		return subefSeq;
	}

	public void setSubefSeq(int subefSeq) {
		this.subefSeq = subefSeq;
	}

	public int getSubefTime() {
		return subefTime;
	}

	public void setSubefTime(int subefTime) {
		this.subefTime = subefTime;
	}

	public String getSubefImage() {
		return subefImage;
	}

	public void setSubefImage(String subefImage) {
		this.subefImage = subefImage;
	}

	public String getSubefName() {
		return subefName;
	}

	public void setSubefName(String subefName) {
		this.subefName = subefName;
	}

	public double getSubefKcal() {
		return subefKcal;
	}

	public void setSubefKcal(double subefKcal) {
		this.subefKcal = subefKcal;
	}

	/*
	public int getSubefAmount() {
		return subefAmount;
	}

	public void setSubefAmount(int subefAmount) {
		this.subefAmount = subefAmount;
	}*/

	public String getSubefType() {
		return subefType;
	}

	public void setSubefType(String subefType) {
		this.subefType = subefType;
	}

	public String getSubefID() {
		return subefID;
	}

	public void setSubefID(String subefID) {
		this.subefID = subefID;
	}

	@Override
	public String toString() {
		return "SubExerMealDto [subefSeq=" + subefSeq + ", subefTime=" + subefTime + ", subefImage=" + subefImage
				+ ", subefName=" + subefName + ", subefKcal=" + subefKcal
				+ ", subefAmount=" /* + subefAmount */
				+ ", subefType=" + subefType + ", subefID=" + subefID + "]";
	}
	

}
