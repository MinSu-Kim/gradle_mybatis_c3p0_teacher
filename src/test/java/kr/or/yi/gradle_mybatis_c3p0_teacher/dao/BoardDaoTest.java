package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import kr.or.yi.gradle_mybatis_c3p0_teacher.AbstractTest;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.BoardDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.SearchCriteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardDaoTest extends AbstractTest {
	private static BoardDao dao = new BoardDaoImpl();
	private static SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
	
	private long bno = 4087;
	@Test
	public void test01getList() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Board> list = dao.getList();
		log.debug("list.size() : " + list.size() );
		log.debug("list.get(0) : " + list.get(0) );
	}
//	@Test
//	public void test02insertBoard() {
//		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
//		Board newBoard = new Board();
//		newBoard.setTitle("새로 작성한 글");
//		newBoard.setContent("새로 작성한 내용");
//		newBoard.setWriter("newBie");
//		
//		int res = dao.insertBoard(newBoard);
//		Assert.assertEquals(1, res);
//	}
	
	@Test
	public void test03readBoard() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Board readBoard = dao.readBoard(10);
		Assert.assertNotNull(readBoard);
		log.debug("readBoard : " + readBoard);
	}
	
	@Test
	public void test04deleteBoard() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = dao.deleteBoard(sqlSession, bno+1);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void test05updateBoard() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Board newBoard = new Board();
		newBoard.setBno(1);
		newBoard.setTitle("수정한 글");
		int res = dao.updateBoard(sqlSession, newBoard);
		Assert.assertEquals(1, res);
		
		newBoard.setContent("수정한 내용");
		res = dao.updateBoard(sqlSession, newBoard);
		Assert.assertEquals(1, res);
		
		newBoard.setWriter("updateBie");
		res = dao.updateBoard(sqlSession, newBoard);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void test05getListCriteria() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(1);	//1page
		cri.setPerPageNum(20);//1page에 20개
		
		List<Board> list = dao.getListCriteria(cri);
		for(Board p : list) {
			log.debug(p.toString());
		}

		Assert.assertNotNull(list);
	}
	
	@Test
	public void test07getNextBno() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = dao.getNextBno();
		log.debug("next Bno : " + res);
		Assert.assertNotEquals(0, res);
	}
	
	@Test
	public void test08getListSearchCriteria() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(1);	//1page
		cri.setPerPageNum(20);//1page에 20개
		cri.setKeyword("%글%");
		cri.setSearchType("t");
		
		log.debug("cri " + cri);
		
		List<Board> list = dao.getListCriteria(cri);
		for(Board p : list) {
			log.debug(p.toString());
		}
		
		Assert.assertNotNull(list);
		
		int res = dao.listSearchCount(cri);
		log.debug("total count : " + res);
		
	}
}
