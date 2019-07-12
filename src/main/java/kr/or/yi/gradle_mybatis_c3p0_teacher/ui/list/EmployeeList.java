package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list;

import javax.swing.SwingConstants;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Employee;

@SuppressWarnings("serial")
public class EmployeeList extends AbstractList<Employee> {

	public EmployeeList(String title) {
		super(title);
	}

	@Override
	protected void tableAlignmentAndWidth() {
		// 사원번호(0), 사원명(1), 급여(2), 부서(3), 성별(4), 입사일(5), 직책(6)
		tableCellAlignment(SwingConstants.CENTER, 0, 1, 3, 4, 5, 6);
		tableCellAlignment(SwingConstants.RIGHT, 2);
		// 사원번호(0), 사원명(1), 급여(2), 부서(3), 성별(4), 입사일(5), 직책(6)
		tableSetWidth(100, 150, 150, 150, 50, 150, 150);
	}

	@Override
	protected Object[] toArray(int idx) {
		Employee emp = itemList.get(idx);
		return emp.toArray();
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { "사원번호", "사원명", "급여", "부서", "성별", "입사일", "직책"};
	}

}
