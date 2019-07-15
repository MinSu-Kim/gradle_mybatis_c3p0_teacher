package kr.or.yi.gradle_mybatis_c3p0_teacher.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import kr.or.yi.gradle_mybatis_c3p0_teacher.AbstractTest;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Department;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Title;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionServiceTest extends AbstractTest {
	private static TransactionService service = new TransactionService();

	@Test(expected = RuntimeException.class)
	public void test1InsertTitleDept() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Title title = new Title(); // 에러
		title.setTitleCode(1);
		title.setTitleName("사원");
		Department department = new Department(6, "H/W개발", 6);
		service.addTitleDeparment(title, department);
	}

	@Test(expected = RuntimeException.class)
	public void test2InsertTitleDept() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Title title = new Title();
		title.setTitleCode(7);
		title.setTitleName("무기계약");
		Department department = new Department(1, "개발", 6);// 에러
		service.addTitleDeparment(title, department);
	}

	@Test
	public void test3InsertTitleDept() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Title title = new Title();
		title.setTitleCode(7);
		title.setTitleName("무기계약");
		Department department = new Department(6, "H/W개발", 6);
		service.addTitleDeparment(title, department);
	}

	@Test(expected = RuntimeException.class)
	public void test4DeleteTitleDept() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Title title = new Title(); // fail
		title.setTitleCode(8);
		title.setTitleName("사원");
		Department department = new Department(6, "H/W개발", 6);
		int res = service.removeTitleDeparment(title, department);
		Assert.assertEquals(1, res);
		log.debug("res = " + res);
	}

	@Test(expected = RuntimeException.class)
	public void test5DeleteTitleDept() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Title title = new Title();
		title.setTitleCode(7);
		title.setTitleName("무기계약");
		Department department = new Department(10, "개발", 6);// fail
		
		int res = service.removeTitleDeparment(title, department);
		Assert.assertEquals(1, res);
		log.debug("res = " + res);
	}

	@Test
	public void test6DeleteTitleDept() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Title title = new Title();
		title.setTitleCode(7);
		title.setTitleName("무기계약");
		Department department = new Department(6, "H/W개발", 6);
		int res = service.removeTitleDeparment(title, department);
		Assert.assertEquals(2, res);
		log.debug("res = " + res);
	}

}
