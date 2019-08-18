package kr.or.yi.gradle_mybatis_c3p0_teacher.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.BoardDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.SearchCriteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class BoardUIService {
	private static final BoardUIService instance = new BoardUIService();
	private BoardDao boardDao;
	
	public static BoardUIService getInstance() {
		return instance;
	}

	private BoardUIService() {
		boardDao = new BoardDaoImpl();
	}
	
	public void register(Board board) {
		int res = 0;
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			res += boardDao.insertBoard(board);
			List<String> files = board.getFiles();
			for(String file : files) {
				res += boardDao.addAttach(file);
			}
			
			if (res == files.size()+1)
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

	public List<Board> getListCriteria(SearchCriteria cri) {
		return boardDao.getListCriteria(cri);
	}

	public int listSearchCount(SearchCriteria cri) {
		return boardDao.listSearchCount(cri);
	}

	public Board readBoard(long bno) {
		return boardDao.readBoard(bno);
	}

	public void updateViewCnt(int bno) {
		boardDao.updateViewCnt(bno);
	}

	public int insertBoard(Board item) {
		return boardDao.insertBoard(item);
	}

	public int deleteBoard(long bno) {
		return boardDao.deleteBoard(bno);
	}

	public int updateBoard(Board board) {
		return boardDao.updateBoard(board);
	}
	
}
