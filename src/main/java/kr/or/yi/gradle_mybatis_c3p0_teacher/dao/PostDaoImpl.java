package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Post;
import kr.or.yi.gradle_mybatis_c3p0_teacher.jdbc.MyBatisSqlSessionFactory;

public class PostDaoImpl implements PostDao {
	private static final String namespace = "kr.or.yi.gradle_mybatis_c3p0_teacher.dao.PostDao";

	@Override
	public List<Post> selectPostByAll() {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".selectPostByAll");
		}
	}

	@Override
	public List<Post> listCriteria(Criteria cri){
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectList(namespace + ".listCriteria", cri);
		}
	}

	@Override
	public int countPaging(Criteria cri) {
		try (SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()) {
			return sqlSession.selectOne(namespace + ".countPaging", cri);
		}
	}

}
