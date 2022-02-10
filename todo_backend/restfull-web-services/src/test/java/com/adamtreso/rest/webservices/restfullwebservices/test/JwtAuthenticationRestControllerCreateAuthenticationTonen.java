package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.adamtreso.rest.webservices.restfullwebservices.common.jwt.JwtTokenUtil;
import com.adamtreso.rest.webservices.restfullwebservices.dto.TokenDto;
import com.adamtreso.rest.webservices.restfullwebservices.dto.UserDto;

import lombok.SneakyThrows;

public class JwtAuthenticationRestControllerCreateAuthenticationTonen extends IntegrationTestBase {

	private final UserDto TESTED_USER_BAD_PASSWORD = new UserDto(TESTED_USER.getId(), TESTED_USER.getUsername(), "wrongpassword");

	private final UserDto NONEXSISTING_USER = new UserDto(-1L, "nonexsistinguser", "wrongpassword");

	@Autowired
	private UserDetailsService jwtDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Test
	@SneakyThrows
	public void testGivenExsistingUserWithCorrectPasswordThenTokenRecieved() {
		// GIVEN
		MockHttpServletRequestBuilder request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TESTED_USER))
				.accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		MvcResult result = resultActions
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.token").exists())
				.andReturn();
		TokenDto resultToken = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDto.class);
		UserDetails userDetails = jwtDetailsService.loadUserByUsername(TESTED_USER.getUsername());
		assertTrue(jwtTokenUtil.validateToken(resultToken.getToken(), userDetails));
	}

	@Test
	@SneakyThrows
	public void testGivenBadPasswordThenUnauthorizedRecieved() {
		// GIVEN
		MockHttpServletRequestBuilder request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TESTED_USER_BAD_PASSWORD))
				.accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		resultActions.andExpect(status().isUnauthorized());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenUnauthorizedRecieved() {
		// GIVEN
		MockHttpServletRequestBuilder request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(NONEXSISTING_USER))
				.accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = mockMvc.perform(request);
		// THEN
		resultActions.andExpect(status().isUnauthorized());
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
