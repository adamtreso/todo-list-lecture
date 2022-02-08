package com.adamtreso.rest.webservices.restfullwebservices.test;

import org.junit.Rule;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.adamtreso.rest.webservices.restfullwebservices.RestfullWebServicesApplication;
import com.adamtreso.rest.webservices.restfullwebservices.service.TodoService;

@Category(IntegrationTestMarker.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfullWebServicesApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class IntegrationTestBase {
	
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	
}
