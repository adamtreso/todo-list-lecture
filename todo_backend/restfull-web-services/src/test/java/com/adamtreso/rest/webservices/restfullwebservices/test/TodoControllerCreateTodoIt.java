package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.adamtreso.rest.webservices.restfullwebservices.dto.TodoDto;
import com.adamtreso.rest.webservices.restfullwebservices.repository.TodoRepository;
import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import lombok.SneakyThrows;

public class TodoControllerCreateTodoIt extends IntegrationTestBase {

	@Autowired
	private TodoRepository todoRepos;

	private final TodoDto TODO_TO_CREATE = new TodoDto(-1L, TESTED_USER.getUsername(), "New value", new Date(), true);
	private final TodoDto TODO_TO_CREATE_NONEXSISTING = new TodoDto(-1L, "nonexsistinguser", "Changed value", new Date(), true);

	@Test
	@SneakyThrows
	public void testGivenExsistingUserThenTodosIsRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = post("/users/" + TODO_TO_CREATE.getUsername() + "/todos/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TODO_TO_CREATE));
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isCreated()).andExpect(redirectedUrlPattern("users/" + TODO_TO_CREATE.getUsername() + "/todos/*"));
		MatcherAssert.assertThat(todoRepos.findByUsernameOrderById(TODO_TO_CREATE.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenNotFoundRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = post("/users/" + TODO_TO_CREATE_NONEXSISTING.getUsername() + "/todos/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TODO_TO_CREATE_NONEXSISTING));
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
		MatcherAssert.assertThat(todoRepos.findByUsernameOrderById(TODO_TO_CREATE.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}
}
