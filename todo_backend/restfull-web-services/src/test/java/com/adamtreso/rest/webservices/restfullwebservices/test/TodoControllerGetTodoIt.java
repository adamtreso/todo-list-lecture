package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import lombok.SneakyThrows;

public class TodoControllerGetTodoIt extends IntegrationTestBase {

	private final Long GET_ID = 1001L;

	@Test
	@SneakyThrows
	public void testGivenExsistingUserThenTodosIsRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request =
				get("/users/" + TESTED_USER.getUsername() + "/todos/" + GET_ID).accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		MvcResult result = resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		MatcherAssert.assertThat(result.getResponse().getContentAsString(), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenNotFoundRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = get("/users/nonexsistinguser/todos/" + GET_ID).accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingIdThenNotFoundRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = get("/users/" + TESTED_USER.getUsername() + "/todos/12345").accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
	}
}
