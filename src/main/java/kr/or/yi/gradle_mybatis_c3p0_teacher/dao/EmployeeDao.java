package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Employee;

public interface EmployeeDao {
	List<Employee> selectEmployeeByAll();
//	int insertTitle(Title title);
//	int deleteTitle(Title title);
//	int updateTitle(Title title);
//	Title selectTitleByCode(Title title);
}
