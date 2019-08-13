package kr.or.yi.gradle_mybatis_c3p0_teacher.dto;

import java.util.Date;

public class Reply {
	private int rno;
	private int bno;
	private String replyText;
	private String replyer;
	private Date regDate;
	private Date updateDate;

	
	public Reply() {
	}

	public Reply(int bno, String replyText, String replyer) {
		this.bno = bno;
		this.replyText = replyText;
		this.replyer = replyer;
	}

	public Reply(int rno, int bno, String replyText, String replyer, Date regDate, Date updateDate) {
		this.rno = rno;
		this.bno = bno;
		this.replyText = replyText;
		this.replyer = replyer;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}



	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public String getReplyer() {
		return replyer;
	}

	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Reply [rno=" + rno + ", bno=" + bno + ", replyText=" + replyText + ", replyer=" + replyer
				+ ", regDate=" + regDate + ", updateDate=" + updateDate + "]";
	}

}
