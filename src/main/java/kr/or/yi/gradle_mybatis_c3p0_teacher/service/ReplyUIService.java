package kr.or.yi.gradle_mybatis_c3p0_teacher.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.BoardDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.ReplyDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.BoardDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.ReplyDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class ReplyUIService {
	
	private static final ReplyUIService instance= new ReplyUIService();
	
	public static ReplyUIService getInstance() {
		return instance;
	}

	private ReplyDao replyDao;
	private BoardDao boardDao;
	
	private ReplyUIService() {
		replyDao = new ReplyDaoImpl();
		boardDao = new BoardDaoImpl();
	}
	
	public void addReply(Reply reply) {
		int res = 0;
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			res += replyDao.insertReply(reply);
			res += boardDao.updateReplyCount(reply.getBno(), 1);
			if (res == 2)
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
	
	public void removeReply(Integer rno) {
		int res = 0;
		SqlSession sqlSession = MyBatisSqlSessionFactory.openSession();
		try {
			int bno = replyDao.getBno(rno);
			res += replyDao.deleteReply(rno);
			res += boardDao.updateReplyCount(bno, -1);
			if (res == 2)
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

	public int count(int bno) {
		return replyDao.count(bno);
	}

	public List<Reply> listPage(int bno, Criteria cri) {
		return replyDao.listPage(bno, cri);
	}

	public void updateReply(Reply reply) {
		replyDao.updateReply(reply);
	}

}
