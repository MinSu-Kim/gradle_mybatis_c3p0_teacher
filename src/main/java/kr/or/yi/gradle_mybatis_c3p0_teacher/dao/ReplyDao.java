package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;

public interface ReplyDao {
	List<Reply> listReply(int bno);
	int insertReply(Reply reply);
	int updateReply(Reply reply);
	int deleteReply(int rno);
	
	//댓글페이징
	List<Reply> listPage(int bno, Criteria cri);
	int count(int bno);
	
	//게시물댓글 관련
	int getBno(Integer rno);
}
