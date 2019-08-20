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

	@Override
	public void insertBoard(SqlSession sqlSession, Board board) {
			sqlSession.insert(namespace + ".insertBoard", board);
	}

	@Override
	public Board readBoard(long bno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectOne(namespace + ".readBoard", bno);
		}
	}

	@Override
	public int deleteBoard(SqlSession sqlSession, long bno) {
		return sqlSession.delete(namespace + ".deleteBoard", bno);
	}

	@Override
	public int updateBoard(SqlSession sqlSession, Board board) {
		return sqlSession.update(namespace + ".updateBoard", board);
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

	@Override
	public void addAttach(SqlSession sqlSession, String fullName) {
		sqlSession.insert(namespace + ".addAttach", fullName);
	}

	@Override
	public List<String> getAttach(Integer bno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".getAttach", bno);
		}
	}

//service에서 sqlSession받아서 처리
	@Override
	public void deleteAttach(SqlSession sqlSession, Integer bno) {
		sqlSession.delete(namespace + ".deleteAttach", bno);
	}

	@Override
	public void replaceAttach(SqlSession sqlSession, String fullName, int bno) {
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fullName", fullName);
		System.out.println(map);
		sqlSession.update(namespace + ".replaceAttach", map);
	}


}
