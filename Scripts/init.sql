-- 마이바티
DROP SCHEMA IF EXISTS mybatis_study;

-- 마이바티
CREATE SCHEMA mybatis_study;

-- 직책
CREATE TABLE mybatis_study.title (
	title_code INTEGER     NOT NULL COMMENT '직책번호', -- 직책번호
	title_name VARCHAR(20) NOT NULL COMMENT '직책명' -- 직책명
)
COMMENT '직책';

-- 직책
ALTER TABLE mybatis_study.title
	ADD CONSTRAINT PK_title -- 직책 기본키
		PRIMARY KEY (
			title_code -- 직책번호
		);

-- 부서
CREATE TABLE mybatis_study.department (
	dept_code INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
	dept_name VARCHAR(20) NOT NULL COMMENT '부서명', -- 부서명
	floor     INTEGER     NULL     COMMENT '위치' -- 위치
)
COMMENT '부서';

-- 부서
ALTER TABLE mybatis_study.department
	ADD CONSTRAINT PK_department -- 부서 기본키
		PRIMARY KEY (
			dept_code -- 부서번호
		);

-- 사원
CREATE TABLE mybatis_study.employee (
	eno      INTEGER     NOT NULL COMMENT '사원번호', -- 사원번호
	ename    VARCHAR(20) NOT NULL COMMENT '사원명', -- 사원명
	salary   INTEGER     NULL     COMMENT '급여', -- 급여
	dno      INTEGER     NULL     COMMENT '부서번호', -- 부서번호
	gender   TINYINT(1)  NULL     COMMENT '성별', -- 성별
	joindate DATE        NOT NULL COMMENT '입사일자', -- 입사일자
	title    INTEGER     NULL     COMMENT '직책' -- 직책
)
COMMENT '사원';

-- 사원
ALTER TABLE mybatis_study.employee
	ADD CONSTRAINT PK_employee -- 사원 기본키
		PRIMARY KEY (
			eno -- 사원번호
		);

-- 사원
ALTER TABLE mybatis_study.employee
	ADD CONSTRAINT FK_department_TO_employee -- 부서 -> 사원
		FOREIGN KEY (
			dno -- 부서번호
		)
		REFERENCES mybatis_study.department ( -- 부서
			dept_code -- 부서번호
		);

-- 사원
ALTER TABLE mybatis_study.employee
	ADD CONSTRAINT FK_title_TO_employee -- 직책 -> 사원
		FOREIGN KEY (
			title -- 직책
		)
		REFERENCES mybatis_study.title ( -- 직책
			title_code -- 직책번호
		);
		
	

-- 계정과 권한부여
grant all privileges 
on mybatis_study.* 
to 'user_mybatis_study'@'localhost' 
identified by 'rootroot';