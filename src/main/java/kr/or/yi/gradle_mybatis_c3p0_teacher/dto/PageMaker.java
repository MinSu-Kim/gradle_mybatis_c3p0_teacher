package kr.or.yi.gradle_mybatis_c3p0_teacher.dto;

public class PageMaker {

	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;

	private int displayPageNum = 10;

	private Criteria cri;

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;

		calcData();
	}

	private void calcData() {

		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);

		startPage = (endPage - displayPageNum) + 1;

		int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));

		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}

		prev = startPage == 1 ? false : true;

		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;

	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	@Override
	public String toString() {
		return String.format(
				"PageMaker [totalCount=%s, startPage=%s, endPage=%s, prev=%s, next=%s, displayPageNum=%s, cri=%s]",
				totalCount, startPage, endPage, prev, next, displayPageNum, cri);
	}

}
