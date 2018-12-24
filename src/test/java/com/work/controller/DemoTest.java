package com.work.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.work.domain.User;
import com.work.service.Userservice;

@RunWith (SpringRunner.class)
@SpringBootTest
public class DemoTest {
	@Autowired
    private Userservice userService;
	
	
	@Test
    public void queryByIdTest() throws Exception {
		 User user = this.userService.queryById(1);
		 System.out.println(user);
    }
}
