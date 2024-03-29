package kr.or.yi.gradle_mybatis_c3p0_teacher.service;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.DepartmentDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.EmployeeDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.TitleDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.DepartmentDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.EmployeeDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.TitleDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Department;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Employee;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Title;

public class EmployeeUIService {
	private TitleDao titleDao;
	private DepartmentDao deptDao;
	private EmployeeDao empDao;

	public EmployeeUIService() {
		titleDao = new TitleDaoImpl();
		deptDao = new DepartmentDaoImpl();
		empDao = new EmployeeDaoImpl();
	}

	public List<Employee> selectEmpAll() {
		return empDao.selectEmployeeByAll();
	}

	public List<Department> selectDeptAll() {
		return deptDao.selectDepartmentByAll();
	}

	public List<Title> selectTitleAll() {
		return titleDao.selectTitleByAll();
	}

	public int updateEmpoyee(Employee item) {
		return empDao.updateEmployee(item);
	}

	public int deleteEmp(Employee item) {
		return empDao.deleteEmployee(item);
	}

	public int createItem(Employee item) {
		return empDao.insertEmployee(item);
	}

	
	
}
