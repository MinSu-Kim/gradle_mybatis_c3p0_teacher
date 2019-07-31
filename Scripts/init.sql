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
		
	
CREATE TABLE mybatis_study.post (
  zipcode char(5) DEFAULT NULL,
  sido varchar(20) DEFAULT NULL,
  sigungu varchar(20) DEFAULT NULL,
  doro varchar(80) DEFAULT NULL,
  building1 int(5) DEFAULT NULL,
  building2 int(5) DEFAULT NULL,
  KEY idx_post_sido (sido),
  KEY idx_post_doro (doro),
  KEY idx_post_doro_building1 (doro,building1)
);
select now() , sysdate(), curdate();

-- 게시판
drop table if exists mybatis_study.tbl_reply;
drop table if exists mybatis_study.tbl_board;

create table mybatis_study.tbl_board (
	bno        int unsigned not null comment '번호', -- 번호
	title      varchar(200) not null comment '제목', -- 제목
	content    text         not null comment '내용', -- 내용
	writer     varchar(50)  not null comment '작성자', -- 작성자
	regdate    datetime         default now() comment '작성일', -- 작성일
	updatedate datetime         default now() comment '수정일' -- 수정일
)comment '게시판';

-- 게시판
ALTER TABLE mybatis_study.tbl_board
	ADD CONSTRAINT PK_tbl_board -- 게시판 기본키
		PRIMARY KEY (
			bno -- 번호
		);
	
ALTER TABLE mybatis_study.tbl_board
	MODIFY COLUMN bno INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '번호';

-- 댓글
CREATE TABLE mybatis_study.tbl_reply (
	rno        INT UNSIGNED NOT NULL COMMENT '댓글번호', -- 댓글번호
	bno        INT UNSIGNED NOT NULL COMMENT '개시판번호', -- 개시판번호
	reply      TEXT         NOT NULL COMMENT '댓글', -- 댓글
	replyer    VARCHAR(50)  NOT NULL COMMENT '작성자', -- 작성자
	replayDate datetime     NULL     DEFAULT now() COMMENT '작성일', -- 작성일
	updateDate datetime     NULL     DEFAULT now() COMMENT '수정일' -- 수정일
)
COMMENT '댓글';

-- 댓글
ALTER TABLE mybatis_study.tbl_reply
	ADD CONSTRAINT PK_tbl_reply -- 댓글 기본키
		PRIMARY KEY (
			rno -- 댓글번호
		);

ALTER TABLE mybatis_study.tbl_reply
	MODIFY COLUMN rno INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '댓글번호';

-- 댓글
ALTER TABLE mybatis_study.tbl_reply
	ADD CONSTRAINT FK_tbl_board_TO_tbl_reply -- 게시판 -> 댓글
		FOREIGN KEY (
			bno -- 개시판번호
		)
		REFERENCES mybatis_study.tbl_board ( -- 게시판
			bno -- 번호
		);	


-- 계정과 권한부여
grant all privileges 
on mybatis_study.* 
to 'user_mybatis_study'@'localhost' 
identified by 'rootroot';