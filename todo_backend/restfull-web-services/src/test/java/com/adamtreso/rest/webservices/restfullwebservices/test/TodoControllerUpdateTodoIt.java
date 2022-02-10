package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.adamtreso.rest.webservices.restfullwebservices.dto.TodoDto;
import com.adamtreso.rest.webservices.restfullwebservices.repository.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import lombok.SneakyThrows;

public class TodoControllerUpdateTodoIt extends IntegrationTestBase {

	@Autowired
	private TodoRepository todoRepos;

	private final TodoDto TODO_TO_UPDATE = new TodoDto(1001L, TESTED_USER.getUsername(), "Changed value", new Date(), true);

	@Test
	@SneakyThrows
	public void testGivenExsistingUserThenTodoIsRecievedAndDatabaseChanged() throws JsonProcessingException {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = put("/users/" + TODO_TO_UPDATE.getUsername() + "/todos/" + TODO_TO_UPDATE.getId())
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TODO_TO_UPDATE))
				.contentType(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		MvcResult result = resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		MatcherAssert.assertThat(result.getResponse().getContentAsString(), sameJsonAsApprovedIgnoringTargetDate().withUniqueId("response"));
		MatcherAssert
				.assertThat(
						todoRepos.findByUsernameOrderById(TODO_TO_UPDATE.getUsername()),
						sameJsonAsApprovedIgnoringTargetDate().withUniqueId("table"));
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenNotFoundRecievedAndDatabaseNotChanged() throws JsonProcessingException {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = put("/users/nonexsistinguser/todos/" + TODO_TO_UPDATE.getId())
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TODO_TO_UPDATE))
				.contentType(MediaType.APPLICATION_JSON);;
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
		MatcherAssert.assertThat(todoRepos.findByUsernameOrderById(TODO_TO_UPDATE.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingIdThenNotFoundRecievedAndDatabaseNotChanged() throws JsonProcessingException {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = put("/users/" + TODO_TO_UPDATE.getUsername() + "/todos/12345")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TODO_TO_UPDATE))
				.contentType(MediaType.APPLICATION_JSON);;
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
		MatcherAssert.assertThat(todoRepos.findByUsernameOrderById(TODO_TO_UPDATE.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}
}
