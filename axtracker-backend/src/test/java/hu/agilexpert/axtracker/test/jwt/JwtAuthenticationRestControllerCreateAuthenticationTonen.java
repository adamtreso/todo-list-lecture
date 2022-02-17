package hu.agilexpert.axtracker.test.jwt;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import hu.agilexpert.axtracker.dto.JwtAuthenticationRequestDto;
import hu.agilexpert.axtracker.dto.JwtTokenDto;
import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.service.JwtTokenService;
import hu.agilexpert.axtracker.service.UserService;
import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class JwtAuthenticationRestControllerCreateAuthenticationTonen extends IntegrationTestBase {

	private final JwtAuthenticationRequestDto EXSISTING_USER_BAD_PASSWORD =
			new JwtAuthenticationRequestDto(EXSISTING_USER.getUsername(), "wrongpassword");
	protected final JwtAuthenticationRequestDto NON_EXSISTING_USER = new JwtAuthenticationRequestDto("nonexsisting", "abc");

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private UserService userService;

	@Test
	@SneakyThrows
	public void testGivenExsistingUserWithCorrectPasswordThenTokenAndUserRecieved() {
		// GIVEN
		MockHttpServletRequestBuilder request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(EXSISTING_USER))
				.accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		MvcResult result = resultActions
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.token").exists())
				.andReturn();
		String token = objectMapper.readValue(result.getResponse().getContentAsString(), JwtTokenDto.class).getToken();
		UserDto user = userService.getUser(EXSISTING_USER.getUsername()).get();
		assertTrue(jwtTokenService.isValidToken(token, user));
	}

	@Test
	@SneakyThrows
	public void testGivenExsistingUserWithBadPasswordThenInvalidCredentialsRecieved() {
		// GIVEN
		MockHttpServletRequestBuilder request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(EXSISTING_USER_BAD_PASSWORD))
				.accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		resultActions.andExpect(status().isUnauthorized()).andExpect(content().string("INVALID_CREDENTIALS"));
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenInvalidCredentialsRecieved() {
		// GIVEN
		MockHttpServletRequestBuilder request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(NON_EXSISTING_USER))
				.accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		resultActions.andExpect(status().isUnauthorized()).andExpect(content().string("INVALID_CREDENTIALS"));
	}

	@Test
	@SneakyThrows
	public void testGivenNothingThenBadRequestRecieved() {
		// GIVEN
		MockHttpServletRequestBuilder request = post("/authenticate").accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		resultActions.andExpect(status().isBadRequest());
	}
}
