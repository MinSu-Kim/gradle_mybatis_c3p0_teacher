package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list;

import javax.swing.SwingConstants;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Post;

@SuppressWarnings("serial")
public class PostList extends AbstractList<Post> {
	
	public PostList() {
		super("주소");
	}
	
	@Override
	protected void tableAlignmentAndWidth() {
		// 직책번호, 직책명은 가운데 정렬
		tableCellAlignment(SwingConstants.CENTER, 0, 1, 2, 3, 4, 5);
		// 직책번호, 직책명의 폭을 (100, 200)으로 가능하면 설정
		tableSetWidth(100, 100, 100, 150, 100, 100);
	}

	@Override
	protected Object[] toArray(int idx) {
		Post title = itemList.get(idx);
		return title.toArray();
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { "우편변호", "시도", "시군구", "도로", "건물번호1", "건물번호2"};
	}

}
