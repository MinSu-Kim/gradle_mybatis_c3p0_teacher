package kr.or.yi.gradle_mybatis_c3p0_teacher.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.BoardDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.SearchCriteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class BoardUIService {
	private static final String BOARD_NAME_SPACE = "kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao";

	
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
			res += sqlSession.insert(BOARD_NAME_SPACE + ".insertBoard", board);
			List<String> files = board.getFiles();
			for(String file : files) {
				res += sqlSession.insert(BOARD_NAME_SPACE + ".addAttach", file);
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

	public int deleteBoard(long bno) {
		int res = 0;
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			res = boardDao.deleteBoard(sqlSession, bno);
			if (res == 1)
				sqlSession.commit();
			else
				throw new Exception();
		}catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			throw new RuntimeException(e.getCause());
		} finally {
			sqlSession.close();
		}
		return res;
	}

	public int updateBoard(Board board) {
		int res = 0;
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			res =  boardDao.updateBoard(sqlSession, board);
			if (res == 1)
				sqlSession.commit();
			else
				throw new Exception();
		}catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			throw new RuntimeException(e.getCause());
		} finally {
			sqlSession.close();
		}
		return res;
	}
	
	public List<String> getAttach(Integer bno){
		return boardDao.getAttach(bno);
	}
	
	public void modify(Board board) {
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			boardDao.updateBoard(sqlSession, board);
			
			Integer bno = (int) board.getBno();
			
			boardDao.deleteAttach(sqlSession, bno);
			
			List<String> files = board.getFiles();
			System.out.println(files);
			if (files != null) {
				for(String fullName : files) {
					boardDao.replaceAttach(sqlSession, fullName, bno);
				}
			}
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			throw new RuntimeException(e.getCause());
		} finally {
			sqlSession.close();
		}
	}
	
	public void remove(Integer bno) {
		int res = 0;
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			boardDao.deleteAttach(sqlSession, bno);
			boardDao.deleteBoard(sqlSession, bno);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			throw new RuntimeException(e.getCause());
		} finally {
			sqlSession.close();
		}
	}
}
