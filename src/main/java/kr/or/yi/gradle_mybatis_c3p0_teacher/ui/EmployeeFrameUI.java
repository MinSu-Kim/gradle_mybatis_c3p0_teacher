package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.util.List;

import javax.swing.JFrame;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Employee;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.AbstractPanel;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.AbstractList;

public class EmployeeFrameUI extends AbstractFrameUI<Employee> {

	public EmployeeFrameUI(String title) {
		super(title);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	protected void initDao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AbstractList<Employee> createListPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AbstractPanel<Employee> createContentPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int updateItem(Employee item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<Employee> getListAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void clearContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int deleteItem(Employee item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int insertItem(Employee item) {
		// TODO Auto-generated method stub
		return 0;
	}

}
