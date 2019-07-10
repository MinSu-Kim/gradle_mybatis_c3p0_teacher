package kr.or.yi.gradle_mybatis_c3p0_teacher.dao;

import java.util.List;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Title;

public interface TitleDao {
	List<Title> selectTitleByAll();
}
