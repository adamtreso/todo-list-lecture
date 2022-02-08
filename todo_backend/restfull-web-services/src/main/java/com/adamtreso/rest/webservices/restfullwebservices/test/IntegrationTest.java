package com.adamtreso.rest.webservices.restfullwebservices.test;


import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.adamtreso.rest.webservices.restfullwebservices.RestfullWebServicesApplication;
import com.adamtreso.rest.webservices.restfullwebservices.repository.TodoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfullWebServicesApplication.class)
public class IntegrationTest {

	@Autowired
	private TodoRepository todoRepos;
	
	@Test
	public void test() {
		assertTrue(todoRepos.count() == 5);
	}
}
