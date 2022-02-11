package hu.agilexpert.axtracker.test.jwt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class JwtAuthenticationRestControllerRefreshAndGetAuthenticationToken extends IntegrationTestBase {

	@Test
	@SneakyThrows
	public void testCorrectTokenThenNewTokenRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = get("/refresh").accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.token").exists());
	}

	@Test
	@SneakyThrows
	public void testGivenNoTokenThenUnauthorizedRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = get("/refresh").accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		resultActions.andExpect(status().isUnauthorized());
	}

	@Test
	@SneakyThrows
	public void testGivenBadTokenThenUnauthorizedRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = get("/refresh").accept(MediaType.APPLICATION_JSON).header(tokenHeader, "NotValidToen");
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		resultActions.andExpect(status().isUnauthorized());
	}
}
