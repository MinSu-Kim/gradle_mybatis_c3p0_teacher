package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll();
	int insertDepartment(Department title);
	int deleteDepartment(Department title);
	int updateDepartment(Department title);
	Department selectDepartmentByCode(Department title);
}
