package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import kr.or.yi.gradle_mybatis_c3p0_teacher.AbstractTest;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.BoardDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;

public class BoardDaoTest extends AbstractTest {
	private static BoardDao dao = new BoardDaoImpl();
	
	@Test
	public void test01getList() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Board> list = dao.getList();
		log.debug("list.size() : " + list.size() );
		log.debug("list.get(0) : " + list.get(0) );
	}
	@Test
	public void test02insertBoard() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Board newBoard = new Board();
		newBoard.setTitle("새로 작성한 글");
		newBoard.setContent("새로 작성한 내용");
		newBoard.setWriter("newBie");
		
		int res = dao.insertBoard(newBoard);
		Assert.assertEquals(1, res);
	}
}
