package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.adamtreso.rest.webservices.restfullwebservices.repository.TodoRepository;
import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import lombok.SneakyThrows;

public class TodoControllerDeleteTodoIt extends IntegrationTestBase {

	@Autowired
	private TodoRepository todoRepos;

	private final Long DELETED_ID = 1001L;

	@Test
	@SneakyThrows
	public void testGivenExsistingUserAndIdThenOkRecievedAndRowDeleted() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = delete("/users/" + TESTED_USER.getUsername() + "/todos/" + DELETED_ID);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isOk());
		MatcherAssert.assertThat(todoRepos.findByUsernameOrderById(TESTED_USER.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenNotFoundRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = delete("/user/nonexsistinguser/todos/" + DELETED_ID);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
		MatcherAssert.assertThat(todoRepos.findByUsernameOrderById(TESTED_USER.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}
}
