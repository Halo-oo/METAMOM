package mul.camp.a.dto;

/* #21# 오늘의 [다이어트] 식단에 사용하는 Dto */
public class SubDietMealDto {
	 
	private int subdfSeq;
	private int subdfTime;
	private String subdfImage;
	private String subdfName;
	//private int subdfKcal;
	private double subdfKcal;		// 아침/점심/저녁/간식에 해당하는 권장 칼로리
	//private int subdfAmount;		// (04.05) 음식 양 제외
	private String subdfType;
	private String subdfID;			// 현재 로그인한 사용자 id, 중복되지 않는 식단 추천을 위하여 사용 
	
	// 하루 권장 칼로리 (아/점/저/간식)
	
	public SubDietMealDto() {
		
	}

	public SubDietMealDto(int subdfSeq, int subdfTime, String subdfImage, String subdfName,
			double subdfKcal/* , int subdfAmount */, String subdfType, String subdfID) {
		super();
		this.subdfSeq = subdfSeq;
		this.subdfTime = subdfTime;
		this.subdfImage = subdfImage;
		this.subdfName = subdfName;
		this.subdfKcal = subdfKcal;
		//this.subdfAmount = subdfAmount;
		this.subdfType = subdfType;
		this.subdfID = subdfID;
	}

	public int getSubdfSeq() {
		return subdfSeq;
	}

	public void setSubdfSeq(int subdfSeq) {
		this.subdfSeq = subdfSeq;
	}

	public int getSubdfTime() {
		return subdfTime;
	}

	public void setSubdfTime(int subdfTime) {
		this.subdfTime = subdfTime;
	}

	public String getSubdfImage() {
		return subdfImage;
	}

	public void setSubdfImage(String subdfImage) {
		this.subdfImage = subdfImage;
	}

	public String getSubdfName() {
		return subdfName;
	}

	public void setSubdfName(String subdfName) {
		this.subdfName = subdfName;
	}

	public double getSubdfKcal() {
		return subdfKcal;
	}

	public void setSubdfKcal(double subdfKcal) {
		this.subdfKcal = subdfKcal;
	}

	/*
	public int getSubdfAmount() {
		return subdfAmount;
	}

	public void setSubdfAmount(int subdfAmount) {
		this.subdfAmount = subdfAmount;
	}*/

	public String getSubdfType() {
		return subdfType;
	}

	public void setSubdfType(String subdfType) {
		this.subdfType = subdfType;
	}

	public String getSubdfID() {
		return subdfID;
	}

	public void setSubdfID(String subdfID) {
		this.subdfID = subdfID;
	}

	@Override
	public String toString() {
		return "SubDietMealDto [subdfSeq=" + subdfSeq + ", subdfTime=" + subdfTime + ", subdfImage=" + subdfImage
				+ ", subdfName=" + subdfName + ", subdfKcal=" + subdfKcal
				+ ", subdfAmount=" /* + subdfAmount */
				+ ", subdfType=" + subdfType + ", subdfID=" + subdfID + "]";
	}

}
