package mul.camp.a.dto;


/* #21# (Web _관리자페이지용) for 검색 + 페이징 + 회원목록 사용 */
public class MemberParam {
	
	private String choice;		// (항목) ID, 이름 중 하나
	private String search; 		// 검색어
	
	private int page; 			// 현재 페이지, 페이지 넘버
	
	private int start; 			// 페이지의 글의 범위 start ~ end
	private int end;
	
	public MemberParam() {
	}

	public MemberParam(String choice, String search, int page, int start, int end) {
		super();
		this.choice = choice;
		this.search = search;
		this.page = page;
		this.start = start;
		this.end = end;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "MemberParam [choice=" + choice + ", search=" + search + ", page=" + page + ", start=" + start + ", end="
				+ end + "]";
	}

}
