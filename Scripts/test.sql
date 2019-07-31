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


select bno, title, content, writer, regdate, updatedate from tbl_board;


