package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;

public interface ReplyDao {
	List<Reply> listReply(int bno);
	int insertReply(Reply reply);
	int updateReply(Reply reply);
	int deleteReply(int rno);
}
