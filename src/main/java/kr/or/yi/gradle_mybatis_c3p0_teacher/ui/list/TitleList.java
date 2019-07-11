package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list;

import javax.swing.SwingConstants;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Title;

@SuppressWarnings("serial")
public class TitleList extends AbstractList<Title> {
	
	public TitleList(String title) {
		super(title);
	}
	
	@Override
	protected void tableAlignmentAndWidth() {
		// 직책번호, 직책명은 가운데 정렬
		tableCellAlignment(SwingConstants.CENTER, 0, 1);
		// 직책번호, 직책명의 폭을 (100, 200)으로 가능하면 설정
		tableSetWidth(150, 300);
	}

	@Override
	protected Object[] toArray(int idx) {
		Title title = itemList.get(idx);
		return title.toArray();
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { "직책번호", "직책명"};
	}

}
