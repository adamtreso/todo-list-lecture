package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adamtreso.rest.webservices.restfullwebservices.dto.TodoDto;
import com.adamtreso.rest.webservices.restfullwebservices.repository.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.karsaig.approvalcrest.jupiter.matcher.Matchers;

public class TodoControllerUpdateTodoIt extends IntegrationTestBase {

	@Autowired
	private TodoRepository todoRepos;

	private final TodoDto todoToUpdate = new TodoDto(1001L, TESTED_USER.getUsername(), "Changed value", new Date(), true);

	@Test
	public void testGivenExsistingUserThenTodosIsRecieved() throws JsonProcessingException {
		// GIVEN
		authenticateTestedUser();

		StringEntity requestEntity = new StringEntity(objectMapper.writeValueAsString(todoToUpdate), ContentType.APPLICATION_JSON);
		HttpPut request = new HttpPut(getServerUrl() + "/users/" + TESTED_USER.getUsername() + "/todos/" + todoToUpdate.getId());
		request.setEntity(requestEntity);
		// WHEN
		HttpResponse response = sendRequestWithToken(request);
		// THEN
		MatcherAssert.assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		MatcherAssert.assertThat(objectMapper.readValue(getResponseBody(response), TodoDto.class), Matchers.sameBeanAs(todoToUpdate));
		MatcherAssert.assertThat(todoRepos.findByUsernameOrderById(TESTED_USER.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	public void testGivenNonExsistingUserThenNotFoundRecieved() throws JsonProcessingException {
		// GIVEN
		authenticateTestedUser();
		StringEntity requestEntity = new StringEntity(objectMapper.writeValueAsString(todoToUpdate), ContentType.APPLICATION_JSON);
		HttpPut request = new HttpPut(getServerUrl() + "/users/nonexsistinguser/todos/" + todoToUpdate.getId());
		request.setEntity(requestEntity);
		// WHEN
		HttpResponse response = sendRequestWithToken(request);
		// THEN
		MatcherAssert.assertThat(response.getStatusLine().getStatusCode(), equalTo(404));
	}
}
