package mul.camp.a.dto;

/*
create table META_SUBSCRIBE_REMEMBERMEALS (
		subrem_seq number not null,
		subrem_name varchar2(50) not null,
		subrem_id varchar2(50) not null,
		subrem_date date not null,
		subrem_mealstime number not null,
		subrem_type number not null,
		CONSTRAINT PK_SUB_REMEMBERMEAL_SEQ PRIMARY KEY(subrem_seq),
		CONSTRAINT FK_SUB_REMEMBERMEAL_NAME FOREIGN KEY(subrem_name) REFERENCES META_FOOD,     
		CONSTRAINT FK_SUB_REMEMBERMEAL_ID FOREIGN KEY(subrem_id) REFERENCES META_MEMBER
	);
*/

/* #21# 추천한 오늘의 식단을 기억하기 위하여 사용하는 Dto */
public class SubMealRememberDto {
	
	private int subremSeq;
	private String subremName;		// 음식 이름
	private String subremId;		// 구독회원 id
	private String subremDate;		// 추천한 날짜
	private int subremMealstime;	// 시간 (아(0) / 점(1) / 저(2) / 간(3))
	private int subremType;			// 유형 (다이어트(0) or 운동(1))
	
	public SubMealRememberDto() {
		
	}

	public SubMealRememberDto(int subremSeq, String subremName, String subremId, String subremDate, int subremMealstime,
			int subremType) {
		super();
		this.subremSeq = subremSeq;
		this.subremName = subremName;
		this.subremId = subremId;
		this.subremDate = subremDate;
		this.subremMealstime = subremMealstime;
		this.subremType = subremType;
	}

	public int getSubremSeq() {
		return subremSeq;
	}

	public void setSubremSeq(int subremSeq) {
		this.subremSeq = subremSeq;
	}

	public String getSubremName() {
		return subremName;
	}

	public void setSubremName(String subremName) {
		this.subremName = subremName;
	}

	public String getSubremId() {
		return subremId;
	}

	public void setSubremId(String subremId) {
		this.subremId = subremId;
	}

	public String getSubremDate() {
		return subremDate;
	}

	public void setSubremDate(String subremDate) {
		this.subremDate = subremDate;
	}

	public int getSubremMealstime() {
		return subremMealstime;
	}

	public void setSubremMealstime(int subremMealstime) {
		this.subremMealstime = subremMealstime;
	}

	public int getSubremType() {
		return subremType;
	}

	public void setSubremType(int subremType) {
		this.subremType = subremType;
	}

	@Override
	public String toString() {
		return "SubMealRememberDto [subremSeq=" + subremSeq + ", subremName=" + subremName + ", subremId=" + subremId
				+ ", subremDate=" + subremDate + ", subremMealstime=" + subremMealstime + ", subremType=" + subremType
				+ "]";
	}
	

}
