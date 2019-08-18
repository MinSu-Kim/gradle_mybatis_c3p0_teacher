package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.SearchCriteria;

public interface BoardDao {
	List<Board> getList();
	
	List<Board> getListCriteria(SearchCriteria cri) ;
	
//	int insertBoard(Board board);
	Board readBoard(long bno);
	int deleteBoard(long bno);
	int updateBoard(Board board);

	int getNextBno();
	
	int listSearchCount(SearchCriteria cri);
	
	//댓글 카운트 처리
	int updateReplyCount(Integer bno, int amount);
	
	//조회 카운트 처리
	int updateViewCnt(Integer bno);
	
	//첨부파일 처리
//	int addAttach(String fullName);
	List<String> getAttach(Integer bno);
}
