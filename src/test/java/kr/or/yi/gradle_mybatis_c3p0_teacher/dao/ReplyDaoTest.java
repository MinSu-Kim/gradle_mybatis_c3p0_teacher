package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import kr.or.yi.gradle_mybatis_c3p0_teacher.AbstractTest;
import kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl.ReplyDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Board;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReplyDaoTest extends AbstractTest {
	private static ReplyDao dao = new ReplyDaoImpl();
	private static Random rnd = new Random(1234);
	
	@Test
	public void testListReply() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		
		int bno = rnd.nextInt(100)+1;
		Reply reply = new Reply(bno, "댓글1", "replyer");
		dao.insertReply(reply);
		
		List<Reply> list = dao.listReply(bno);
		log.debug("list.size() : " + list.size() );
		log.debug("list.get(0) : " + list.get(0) );
		Assert.assertNotNull(list);
	}

	@Test
	public void testInsertReply() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		
		Reply reply = new Reply(rnd.nextInt(100)+1, "댓글1", "replyer");
		int res = dao.insertReply(reply);
		log.debug(String.format("reply(%s) res : %d", reply, res));
		Assert.assertEquals(1, res);
	}

	@Test
	public void testUpdateReply() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int bno = rnd.nextInt(100)+1;
		Reply reply = new Reply(bno, "댓글1", "replyer");
		dao.insertReply(reply);
		
		List<Reply> list = dao.listReply(bno);
		
		Reply upReply = list.get(0);
		log.debug(String.format("before - reply(%s)", reply));
		
		upReply.setReplyText(upReply.getReplyText()+"aaaaaaa");
		upReply.setUpdateDate(new Date());
		
		int res = dao.updateReply(upReply);
		Assert.assertEquals(1, res);
	}

	@Test
	public void testDeleteReply() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int bno = rnd.nextInt(100)+1;
		Reply reply = new Reply(bno, "댓글1", "replyer");
		int res = dao.insertReply(reply);
		
		List<Reply> list = dao.listReply(bno);
		log.debug(list.get(0).toString());
		
		dao.deleteReply(list.get(0).getRno());
		
		list = dao.listReply(bno);
		log.debug(list.get(0).toString());
	}

}
