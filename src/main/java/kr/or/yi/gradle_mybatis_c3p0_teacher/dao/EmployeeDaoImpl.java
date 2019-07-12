package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Employee;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class EmployeeDaoImpl implements EmployeeDao {
	private static final String namespace = "kr.or.yi.gradle_mybatis_c3p0_teacher.dao.EmployeeDao";
	
	@Override
	public List<Employee> selectEmployeeByAll() {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".selectEmployeeByAll");
		}
	}

}
