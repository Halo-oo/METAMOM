package mul.camp.a.dto;

public class SnsDto {
	
	private int seq;
	private String id;
	private String nickname;
	private String profile;
	private String snsdate;
	private String imagecontent;
	private int likecount;
	private int commentcount;
	private String content;
	public SnsDto(int seq, String id, String nickname, String profile, String snsdate, String imagecontent,
			int likecount, int commentcount, String content) {
		super();
		this.seq = seq;
		this.id = id;
		this.nickname = nickname;
		this.profile = profile;
		this.snsdate = snsdate;
		this.imagecontent = imagecontent;
		this.likecount = likecount;
		this.commentcount = commentcount;
		this.content = content;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getSnsdate() {
		return snsdate;
	}
	public void setSnsdate(String snsdate) {
		this.snsdate = snsdate;
	}
	public String getImagecontent() {
		return imagecontent;
	}
	public void setImagecontent(String imagecontent) {
		this.imagecontent = imagecontent;
	}
	public int getLikecount() {
		return likecount;
	}
	public void setLikecount(int likecount) {
		this.likecount = likecount;
	}
	public int getCommentcount() {
		return commentcount;
	}
	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "SnsDto [seq=" + seq + ", id=" + id + ", nickname=" + nickname + ", profile=" + profile + ", snsdate="
				+ snsdate + ", imagecontent=" + imagecontent + ", likecount=" + likecount + ", commentcount="
				+ commentcount + ", content=" + content + "]";
	}
	
	
	
	
	
	
	
}
