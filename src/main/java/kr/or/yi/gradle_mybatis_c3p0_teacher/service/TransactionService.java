package kr.or.yi.gradle_mybatis_c3p0_teacher.service;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Department;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Title;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class TransactionService {
	private static final String DEPT_NS = "kr.or.yi.gradle_mybatis_c3p0_teacher.dao.DepartmentDao";
	private static final String TITLE_NS = "kr.or.yi.gradle_mybatis_c3p0_teacher.dao.TitleDao";

	public void addTitleDeparment(Title title, Department department) {
		int res = 0;
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			res += sqlSession.insert(DEPT_NS + ".insertDepartment", department);
			res += sqlSession.insert(TITLE_NS + ".insertTitle", title);
			if (res == 2)
				sqlSession.commit();
			else
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			throw new RuntimeException(e.getCause());
		} finally {
			sqlSession.close();
		}
	}

	public int removeTitleDeparment(Title title, Department department) {
		int res = 0;
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			res += sqlSession.delete(DEPT_NS + ".deleteDepartment", department);
			res += sqlSession.delete(TITLE_NS + ".deleteTitle", title);
			if (res == 2)
				sqlSession.commit();
			else
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			throw new RuntimeException(e.getCause());
		} finally {
			sqlSession.close();
		}
		return res;
	}

}
