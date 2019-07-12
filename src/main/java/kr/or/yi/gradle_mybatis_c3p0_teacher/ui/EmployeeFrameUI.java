package kr.or.yi.gradle_mybatis_c3p0_teacher.ui;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.DepartmentDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.DepartmentDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.EmployeeDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.EmployeeDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.TitleDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.TitleDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Employee;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.AbstractPanel;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.content.PanelEmployee;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.AbstractList;
import kr.or.yi.gradle_mybatis_c3p0_teacher.ui.list.EmployeeList;

@SuppressWarnings("serial")
public class EmployeeFrameUI extends AbstractFrameUI<Employee> {

	private EmployeeDao empDao;
	private DepartmentDao deptDao;
	private TitleDao titleDao;
	
	public EmployeeFrameUI() {
		super("사원관리");
	}

	@Override
	protected void initDao() {
		empDao = new EmployeeDaoImpl();
		deptDao= new DepartmentDaoImpl();
		titleDao= new TitleDaoImpl();
	}

	@Override
	protected AbstractList<Employee> createListPanel() {
		return new EmployeeList("사원");
	}

	@Override
	protected AbstractPanel<Employee> createContentPanel() {
		PanelEmployee empPanel = new PanelEmployee();
		empPanel.setDeptList(deptDao.selectDepartmentByAll());
		empPanel.setTitleList(titleDao.selectTitleByAll());
		return empPanel;
	}

	@Override
	protected void clearContent() {
		pContent.clearComponent(itemList.size() == 0 ? 1 : itemList.size() + 1);		
	}

	@Override
	protected List<Employee> getListAll() {
		return empDao.selectEmployeeByAll();
	}

	@Override
	protected int updateItem(Employee item) {
		return 0;
	}

	@Override
	protected int insertItem(Employee item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int deleteItem(Employee item) {
		// TODO Auto-generated method stub
		return 0;
	}

}
