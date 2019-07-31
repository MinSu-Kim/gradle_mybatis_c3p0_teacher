package kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class BoardDaoImpl implements BoardDao {
	private static final String namespace = "kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao";

	@Override
	public List<Board> getList() {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".getList");
		}
	}

	@Override
	public int insertBoard(Board board) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.insert(namespace + ".insertBoard", board);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public Board readBoard(long bno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectOne(namespace + ".readBoard", bno);
		}
	}

	@Override
	public int deleteBoard(long bno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.delete(namespace + ".deleteBoard", bno);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public int updateBoard(Board board) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.update(namespace + ".updateBoard", board);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public List<Board> getListCriteria(Criteria cri) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".getListCriteria", cri);
		}
	}

	@Override
	public int countPaging() {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectOne(namespace + ".countPaging");
		}
	}

}
