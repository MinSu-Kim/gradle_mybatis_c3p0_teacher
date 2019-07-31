package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Criteria;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Post;

public interface PostDao {
	List<Post> selectPostByAll();
	
	List<Post> listCriteria(Criteria cri) ;
	int countPaging();
}
