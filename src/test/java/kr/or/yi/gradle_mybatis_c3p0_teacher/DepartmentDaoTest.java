package kr.or.yi.gradle_mybatis_c3p0_teacher;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.DepartmentDao;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dao.DepartmentDaoImpl;
import kr.or.yi.gradle_mybatis_c3p0_teacher.dto.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest extends AbstractTest {
	private static DepartmentDao deptDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		deptDao = new DepartmentDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		deptDao = null;
	}

	@Test
	public void test01SelectDepartmentByAll() {
		log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Department> deptList = deptDao.selectDepartmentByAll();
		Assert.assertNotNull(deptList);
		
		for(Department dept : deptList) {
			log.debug(String.format("%d %s %s", dept.getDeptCode(), dept.getDeptName(), dept.getFloor()));
		}
	}

}
