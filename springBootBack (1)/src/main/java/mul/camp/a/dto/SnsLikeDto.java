package mul.camp.a.dto;

public class SnsLikeDto {
	private int likeseq;
	private String id;
	private String likedate;
	
	public SnsLikeDto() {
		
	}

	public SnsLikeDto(int likeseq, String id, String likedate) {
		super();
		this.likeseq = likeseq;
		this.id = id;
		this.likedate = likedate;
	}

	public int getLikeseq() {
		return likeseq;
	}

	public void setLikeseq(int likeseq) {
		this.likeseq = likeseq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLikedate() {
		return likedate;
	}

	public void setLikedate(String likedate) {
		this.likedate = likedate;
	}

	@Override
	public String toString() {
		return "SnsLikeDto [likeseq=" + likeseq + ", id=" + id + ", likedate=" + likedate + "]";
	}
	
	
	
}
