package mul.camp.a.dto;

public class SubscribeDto {
	
	private String subId;
	private int subType;
	private int subPeriod;
	private int subMorning;
	private int subLunch;
	private int subDinner;
	private int subSnack;
	private String subStartday;
	private String subEndday;
	
	public SubscribeDto() { }

	public SubscribeDto(String subId, int subType, int subPeriod, int subMorning, int subLunch, int subDinner,
			int subSnack, String subStartday, String subEndday) {
		super();
		this.subId = subId;
		this.subType = subType;
		this.subPeriod = subPeriod;
		this.subMorning = subMorning;
		this.subLunch = subLunch;
		this.subDinner = subDinner;
		this.subSnack = subSnack;
		this.subStartday = subStartday;
		this.subEndday = subEndday;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public int getSubPeriod() {
		return subPeriod;
	}

	public void setSubPeriod(int subPeriod) {
		this.subPeriod = subPeriod;
	}

	public int getSubMorning() {
		return subMorning;
	}

	public void setSubMorning(int subMorning) {
		this.subMorning = subMorning;
	}

	public int getSubLunch() {
		return subLunch;
	}

	public void setSubLunch(int subLunch) {
		this.subLunch = subLunch;
	}

	public int getSubDinner() {
		return subDinner;
	}

	public void setSubDinner(int subDinner) {
		this.subDinner = subDinner;
	}

	public int getSubSnack() {
		return subSnack;
	}

	public void setSubSnack(int subSnack) {
		this.subSnack = subSnack;
	}

	public String getSubStartday() {
		return subStartday;
	}

	public void setSubStartday(String subStartday) {
		this.subStartday = subStartday;
	}

	public String getSubEndday() {
		return subEndday;
	}

	public void setSubEndday(String subEndday) {
		this.subEndday = subEndday;
	}

	@Override
	public String toString() {
		return "SubscribeDto [subId=" + subId + ", subType=" + subType + ", subPeriod=" + subPeriod + ", subMorning="
				+ subMorning + ", subLunch=" + subLunch + ", subDinner=" + subDinner + ", subSnack=" + subSnack
				+ ", subStartday=" + subStartday + ", subEndday=" + subEndday + "]";
	}


}
