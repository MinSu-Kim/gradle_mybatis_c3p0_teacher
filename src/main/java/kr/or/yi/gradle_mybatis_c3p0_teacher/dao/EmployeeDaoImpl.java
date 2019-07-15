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

	@Override
	public int insertEmployee(Employee employee) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.update(namespace + ".insertEmployee", employee);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public int deleteEmployee(Employee employee) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.update(namespace + ".deleteEmployee", employee);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public int updateEmployee(Employee employee) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.update(namespace + ".updateEmployee", employee);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public Employee selectEmployeeByCode(Employee employee) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectOne(namespace + ".selectEmployeeByCode", employee);
		}
	}

}
