package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.hamcrest.CoreMatchers.equalTo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adamtreso.rest.webservices.restfullwebservices.repository.TodoRepository;

public class TodoControllerDeleteTodoIt extends IntegrationTestBase {

	@Autowired
	private TodoRepository todoRepos;

	@Test
	public void testGivenExsistingUserAndIdThenOkRecievedAndRowDeleted() {
		// GIVEN
		authenticateTestedUser();
		HttpUriRequest request = new HttpDelete(getServerUrl() + "/users/" + TESTED_USER.getUsername() + "/todos/1001");
		// WHEN
		HttpResponse response = sendRequestWithToken(request);
		// THEN
		MatcherAssert.assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		MatcherAssert.assertThat(todoRepos.findByUsernameOrderById(TESTED_USER.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	public void testGivenNonExsistingUserThenNotFoundRecieved() {
		// GIVEN
		authenticateTestedUser();
		HttpUriRequest request = new HttpDelete(getServerUrl() + "/users/nonexsistinguser/todos/1001");
		// WHEN
		HttpResponse response = sendRequestWithToken(request);
		// THEN
		MatcherAssert.assertThat(response.getStatusLine().getStatusCode(), equalTo(404));
	}
}
