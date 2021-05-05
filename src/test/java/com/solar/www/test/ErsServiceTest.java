package com.solar.www.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.solar.www.dao.ErsDaoImpl;
import com.solar.www.model.Reimb;
import com.solar.www.model.User;
import com.solar.www.service.ErsService;

public class ErsServiceTest {
	@Mock
	ErsDaoImpl fakeDao;
	ErsService serv;

	@BeforeEach
	public void initialize() {
		MockitoAnnotations.initMocks(this);
		// 正常需要和数据库交互时，是 serv = new ErsService(new ErsDaoImpl());
		// 但是因为我是在做JUnit test，所以不要有这个new ErsDaoImpl()实例。
		// fakeDao只是个引用，值为null。我需要它来冒充这个真实的Dao实例。
		serv = new ErsService(fakeDao);
		Reimb myReimb = new Reimb(10, 0, "", "", "", null, 25, 52, 8, 30);

		when(this.fakeDao.getSpecificReimbById(10)).thenReturn(myReimb);
		when(this.fakeDao.getTypeNameById(30)).thenReturn("typeName is 30");
		when(this.fakeDao.getUserNameById(25)).thenReturn("author is 25");
		when(this.fakeDao.getUserNameById(52)).thenReturn("resolver is 52");
		when(this.fakeDao.getStatusNameById(8)).thenReturn("status is 8");

		when(this.fakeDao.createNewUser("hello", "world", "Hello", "World", "helloworld@program.com", 2)).thenReturn(1);
		when(this.fakeDao.createNewUser("angular", "javascript", "project", "timer", "helloworld@program.com", 1))
				.thenReturn(1);
		when(this.fakeDao.createNewUser("", "Cat", "Tom", "Cat", "tomcat@apache.com", 1)).thenReturn(-1);
		when(this.fakeDao.createNewUser("", "gaghasf", "aggwg", "geagghr", "tomcat@apache.com", 1)).thenReturn(-1);
		when(this.fakeDao.createNewUser("Tom", "Cat", "Tom", "Cat", "", 1)).thenReturn(-1);
		when(this.fakeDao.createNewUser("reghas", "kshrj", "jg54gh", "jhtysg", "", 1)).thenReturn(-1);
		when(this.fakeDao.createNewUser("Tom", "", "Tom", "Cat", "ghdasg@rjsak.wew", 1)).thenReturn(-1);
		when(this.fakeDao.createNewUser("rtbiaj", "", "hrtsa", "hhahg", "ghdasg@rjsak.wew", 1)).thenReturn(-1);
	}

	@Test
	public void constructReimbViewTest() {
		assertEquals("typeName is 30", this.serv.constructReimbView(10).getTypeName());
		assertEquals("author is 25", this.serv.constructReimbView(10).getAuthorName());
		assertEquals("resolver is 52", this.serv.constructReimbView(10).getResolverName());
		assertEquals("status is 8", this.serv.constructReimbView(10).getStatusName());
	}

	@Test
	public void processRegisterTestTrue() {
		assertTrue(this.serv.processRegister("hello", "world", "Hello", "World", "helloworld@program.com", "2"));
		assertTrue(
				this.serv.processRegister("angular", "javascript", "project", "timer", "helloworld@program.com", "1"));
	}

	@Test
	public void processRegisterTestFalseNoUserName() {
		assertFalse(this.serv.processRegister("", "Cat", "Tom", "Cat", "tomcat@apache.com", "1"));
		assertFalse(this.serv.processRegister("", "gaghasf", "aggwg", "geagghr", "tomcat@apache.com", "1"));
	}

	@Test
	public void processRegisterTestFalseNoPassWord() {
		assertFalse(this.serv.processRegister("Tom", "", "Tom", "Cat", "ghdasg@rjsak.wew", "1"));
		assertFalse(this.serv.processRegister("rtbiaj", "", "hrtsa", "hhahg", "ghdasg@rjsak.wew", "1"));
	}

	@Test
	public void processRegisterTestFalseNoEmail() {
		assertFalse(this.serv.processRegister("Tom", "Cat", "Tom", "Cat", "", "1"));
		assertFalse(this.serv.processRegister("reghas", "kshrj", "jg54gh", "jhtysg", "", "1"));
	}

}
