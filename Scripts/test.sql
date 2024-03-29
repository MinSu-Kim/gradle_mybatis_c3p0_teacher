select user(), database();
select title_code, title_name from title;
select title_code, title_name from title;
select dept_code, dept_name, floor from department;
desc employee;

select * from employee;

select eno, ename, salary, dept_code, dept_name, floor, gender, joindate, title_code, title_name 
from employee e join department d on e.dno = d.dept_code
     join title t on e.title = t.title_code;
     
    
call salary_total(2);

SELECT bno, title, content, writer, regdate, viewcnt 
FROM tbl_board

where title like '%제목%' or content like '%내용5';


explain
select bno, title, content, writer, regdate, viewcnt
from tbl_board 
order by bno desc
limit 1, 2;

insert into tbl_board(bno, title, content, writer, regdate, viewcnt) 
values(0, '', '', '', current_timestamp, 0);


SELECT count(*) FROM tbl_board;

select rno, bno, replytext, replyer, regdate, updatedate from tbl_reply where bno=4121;
select * from tbl_reply;
select bno from tbl_reply where rno = 68;

select * from tbl_attach;
