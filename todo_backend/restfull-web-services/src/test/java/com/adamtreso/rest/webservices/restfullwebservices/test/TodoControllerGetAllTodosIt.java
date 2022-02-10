package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.hamcrest.CoreMatchers.equalTo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class TodoControllerGetAllTodosIt extends IntegrationTestBase {
	@Test
	public void testGivenExsistingUserThenTodosIsRecieved() {
		// GIVEN
		authenticateTestedUser();
		HttpUriRequest request = new HttpGet(getServerUrl() + "/users/" + TESTED_USER.getUsername() + "/todos");
		// WHEN
		HttpResponse response = sendRequestWithToken(request);
		// THEN
		MatcherAssert.assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		MatcherAssert.assertThat(getResponseBody(response), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	public void testGivenNonExsistingUserThenNotFoundRecieved() {
		// GIVEN
		authenticateTestedUser();
		HttpUriRequest request = new HttpGet(getServerUrl() + "/users/nonexsistinguser/todos");
		// WHEN
		HttpResponse response = sendRequestWithToken(request);
		// THEN
		MatcherAssert.assertThat(response.getStatusLine().getStatusCode(), equalTo(404));
	}
}
