package kr.or.yi.gradle_mybatis_c3p0_teacher.dto;

import java.util.Date;

public class Board {
	private long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private int viewCnt;

	public Board() {
		// TODO Auto-generated constructor stub
	}

	public Board(long bno) {
		this.bno = bno;
	}

	public Board(String title, String content, String writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	public Board(long bno, String title, String content, String writer, Date regdate, int viewCnt) {
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.regdate = regdate;
		this.viewCnt = viewCnt;
	}

	public long getBno() {
		return bno;
	}

	public void setBno(long bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	@Override
	public String toString() {
		return String.format("Board [bno=%s, title=%s, content=%s, writer=%s, regdate=%s, viewCnt=%s]", bno, title,
				content, writer, regdate, viewCnt);
	}

	public Object[] toArray() {
		return new Object[] { bno, title, writer, String.format("%tF", regdate), viewCnt };
	}

}
