package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;

public interface BoardDao {
	List<Board> getList();
	
	List<Board> getListCriteria(Criteria cri) ;
	int countPaging();
	
	int insertBoard(Board board);
	Board readBoard(long bno);
	int deleteBoard(long bno);
	int updateBoard(Board board);
}
