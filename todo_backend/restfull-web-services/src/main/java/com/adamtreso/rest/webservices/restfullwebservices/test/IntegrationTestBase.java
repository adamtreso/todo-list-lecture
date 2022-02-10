package com.adamtreso.rest.webservices.restfullwebservices.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.adamtreso.rest.webservices.restfullwebservices.RestfullWebServicesApplication;
import com.adamtreso.rest.webservices.restfullwebservices.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.karsaig.approvalcrest.jupiter.matcher.Matchers;
import com.github.karsaig.approvalcrest.matcher.GsonConfiguration;
import com.github.karsaig.approvalcrest.matcher.JsonMatcher;

import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfullWebServicesApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class IntegrationTestBase {
	@LocalServerPort
	private int PORT;

	private final String SERVER_DOMAIN = "http://localhost";
	protected final UserDto TESTED_USER = new UserDto(1L, "tadam", "tadam12345");

	protected final ObjectMapper objectMapper = new ObjectMapper();
	private TokenJson token;

	protected JsonMatcher<Object> sameJsonAsApprovedIgnoringTargetDate() {
		return sameJsonAsApproved().ignoring("targetDate");
	}

	protected JsonMatcher<Object> sameJsonAsApproved() {
		GsonConfiguration gsonConfiguration = new GsonConfiguration();
		gsonConfiguration.addTypeAdapterFactory(DateTypeAdapterForJsonMatcher.FACTORY);
		return Matchers.sameJsonAsApproved().withGsonConfiguration(gsonConfiguration);
	}

	@SneakyThrows
	protected void authenticateTestedUser() {
		StringEntity requestEntity = new StringEntity(objectMapper.writeValueAsString(TESTED_USER), ContentType.APPLICATION_JSON);
		HttpPost postMethod = new HttpPost(getServerUrl() + "/authenticate");
		postMethod.setEntity(requestEntity);
		HttpResponse response = HttpClientBuilder.create().build().execute(postMethod);
		token = objectMapper.readValue(getResponseBody(response), TokenJson.class);
	}

	@SneakyThrows
	protected HttpResponse sendRequestWithToken(final HttpUriRequest request) {
		request.setHeader("Authorization", "Bearer " + token.getToken());
		return HttpClientBuilder.create().build().execute(request);
	}

	@SneakyThrows
	protected String getResponseBody(final HttpResponse response) {
		return new BasicResponseHandler().handleResponse(response);
	}

	protected String getServerUrl() {
		return SERVER_DOMAIN + ":" + PORT;
	}
}
