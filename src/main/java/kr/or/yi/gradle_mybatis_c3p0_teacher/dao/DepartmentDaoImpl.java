package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Department;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class DepartmentDaoImpl implements DepartmentDao {
	private static final String namespace = "mappers.DepartmentMapper";

	@Override
	public List<Department> selectDepartmentByAll() {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".selectDepartmentByAll");
		}
	}

	@Override
	public int insertDepartment(Department title) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteDepartment(Department title) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDepartment(Department title) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Department selectDepartmentByCode(Department title) {
		// TODO Auto-generated method stub
		return null;
	}

}
