package kr.or.yi.gradle_mybatis_c3p0_teacher.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.ReplyDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Reply;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class ReplyDaoImpl implements ReplyDao {
	private static final String namespace = "kr.or.yi.gradle_mybatis_c3p0_teacher.dao.ReplyDao";

	@Override
	public List<Reply> listReply(int bno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".listReply", bno);
		}
	}

	@Override
	public int insertReply(Reply reply) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.insert(namespace + ".insertReply", reply);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public int updateReply(Reply reply) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.update(namespace + ".updateReply", reply);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public int deleteReply(int rno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			int res = sqlSession.delete(namespace + ".deleteReply", rno);
			sqlSession.commit();
			return res;
		}
	}

	@Override
	public List<Reply> listPage(int bno, Criteria cri) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("cri", cri);
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".listPage", map);
		}
	}

	@Override
	public int count(int bno) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectOne(namespace + ".count", bno);
		}
	}

}
