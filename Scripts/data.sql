insert into department(dept_code, dept_name, floor) values 
(1,'영업',8),(2,'기획',10),(3, '개발',9),(4,'총무',7);

insert into title(title_code, title_name) values 
(1, '사장'), (2, '부장'), (3, '과장'), (4, '대리'), (5, '사원');

INSERT INTO employee (eno, ename, title, salary, gender, dno, joindate)
VALUES 
(4377,'이성래',1,5000000,1, 2, '2015-03-01'),
(3011,'이수민',2,4000000,1, 3, '2015-04-01'),
(3426,'박영권',3,3000000,1, 1, '2016-05-01'),
(1003,'조민희',3,3000000,0, 2, '2016-06-01'),
(1365,'김상원',5,1500000,0, 1, '2019-05-01'),
(2106,'김창섭',4,2500000,0, 2, '2018-08-01'),
(3427,'최종철',4,1500000,0, 2, '2019-03-01');

-- procedure 생성
DROP PROCEDURE IF EXISTS salary_total;
DELIMITER $$
$$
CREATE PROCEDURE salary_total(in deptno int)
BEGIN
    select dept_name, ifnull(sum(salary),0) as total
    from employee e right join department d on e.dno = d.dept_code
    where dno = deptno;
END$$
DELIMITER ;

call salary_total(2);


LOAD data LOCAL INFILE 'D:\\zipcode_DB\\대구광역시.txt' 
INTO table post   
character set 'euckr'  
fields TERMINATED by '|' 
IGNORE 1 lines 
(@zipcode, @sido, @d, @sigungu , @d, @d, @d, @d, @doro, @d, @d, @building1, @building2, @d, @d, @d, @d, @d, @d ,@d, @d, @d, @d, @d, @d, @d) 
set zipcode=@zipcode, sido=@sido, sigungu=@sigungu, doro=@doro, building1=@building1, building2=@building2
