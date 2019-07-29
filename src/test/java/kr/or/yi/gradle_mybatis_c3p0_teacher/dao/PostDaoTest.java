package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import org.junit.Test;

import kr.or.yi.gradle_mybatis_c3p0_teacher.AbstractTest;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Post;

public class PostDaoTest extends AbstractTest {
	private static PostDao postDao = new PostDaoImpl();


	@Test
	public void testSelectPostByAll() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Post> list = postDao.selectPostByAll();
		log.debug("list.size() : " + list.size() );
	}

}
