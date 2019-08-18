package kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.SearchCriteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class BoardDaoImpl implements BoardDao {
	private static final String namespace = "kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao";
	
	@Override
	public List<Board> getList() {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".getList");
		}
	}

//	@Override
//	public int insertBoard(Board board) {
//		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
//			int res = sqlSession.insert(namespace + ".insertBoard", board);
//			sqlSession.commit();
//			return res;
//		}
//	}

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
	public List<Board> getListCriteria(SearchCriteria cri) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".getListCriteria", cri);
		}
	}

	@Override
	public int getNextBno() {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectOne(namespace + ".getNextBno");
		}
	}

	@Override
	public int listSearchCount(SearchCriteria cri) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectOne(namespace + ".listSearchCount", cri);
		}
	}

	@Override
	public int updateReplyCount(Integer bno, int amount) {
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("amount", amount);
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.delete(namespace + ".updateReplyCount", map);
			sqlSession.commit();
			return res;
		}		
	}

	@Override
	public int updateViewCnt(Integer bno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.insert(namespace + ".updateViewCnt", bno);
			sqlSession.commit();
			return res;
		}
	}

//	@Override
//	public int addAttach(String fullName) {
//		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
//			int res = sqlSession.insert(namespace + ".addAttach", fullName);
//			sqlSession.commit();
//			return res;
//		}
//	}

	@Override
	public List<String> getAttach(Integer bno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".getAttach", bno);
		}
	}

}
