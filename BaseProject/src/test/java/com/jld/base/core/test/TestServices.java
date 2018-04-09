package com.jld.base.core.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jld.base.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:WEB-INF/spring/security/spring-security-context.xml",
		"classpath:WEB-INF/spring/jdbc/spring-persistence-context.xml" })
public class TestServices {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void checkCache() throws InterruptedException {
		
		// First call will take much longer than the rest of the calls.
		
		long init = 0;
		
		init = System.currentTimeMillis();
		userService.getAllUsergroups();
		long t1 = System.currentTimeMillis() - init;
		
		init = System.currentTimeMillis();
		userService.getAllUsergroups();
		long t2 = System.currentTimeMillis() - init;
		
		init = System.currentTimeMillis();
		userService.getAllUsergroups();
		long t3 = System.currentTimeMillis() - init;
		
		init = System.currentTimeMillis();
		userService.getAllUsergroups();
		long t4 = System.currentTimeMillis() - init;
		
		init = System.currentTimeMillis();
		userService.getAllUsergroups();
		long t5 = System.currentTimeMillis() - init;
		
		System.out.println("t1:  " + t1 + "ms");
		System.out.println("t2:  " + t2 + "ms");
		System.out.println("t3:  " + t3 + "ms");
		System.out.println("t4:  " + t4 + "ms");
		System.out.println("t5:  " + t5 + "ms");
		
		// Assert True if t1 >> t2,t3,t4,t5
		assertTrue(t1 > (t2 + t3 + t4 + t5));
	}

}
