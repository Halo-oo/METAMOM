package mul.camp.a.dto;

/*
CREATE TABLE SNS_COMMENT(
    CMTSEQ NUMBER(8),
	SEQ NUMBER(8),
    ID VARCHAR2(50),
	NICKNAME VARCHAR2(50),
	PROFILE VARCHAR2(4000),
	WDATE DATE,
	CONTENT VARCHAR2(2000)
);

ALTER TABLE BBSCOMMENT
ADD CONSTRAINT FK_COMMENT_ID FOREIGN KEY(ID)
REFERENCES MEMBER(ID);
*/

public class CommentDto {
	private int cmtseq;
	private int seq;
	private String id;
	private String wdate;
	private String content;
	
	public CommentDto(){		
	}

	public CommentDto(int cmtseq, int seq, String id, String wdate, String content) {
		super();
		this.cmtseq = cmtseq;
		this.seq = seq;
		this.id = id;
		this.wdate = wdate;
		this.content = content;
	}

	public int getCmtseq() {
		return cmtseq;
	}

	public void setCmtseq(int cmtseq) {
		this.cmtseq = cmtseq;
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

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentDto [cmtseq=" + cmtseq + ", seq=" + seq + ", id=" + id + ", wdate=" + wdate + ", content="
				+ content + "]";
	}
	
	

	

}
