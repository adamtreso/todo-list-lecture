package com.adamtreso.rest.webservices.restfullwebservices.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.adamtreso.rest.webservices.restfullwebservices.RestfullWebServicesApplication;
import com.adamtreso.rest.webservices.restfullwebservices.dto.TokenDto;
import com.adamtreso.rest.webservices.restfullwebservices.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.karsaig.approvalcrest.jupiter.matcher.Matchers;
import com.github.karsaig.approvalcrest.matcher.GsonConfiguration;
import com.github.karsaig.approvalcrest.matcher.JsonMatcher;

import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfullWebServicesApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public abstract class IntegrationTestBase {

	@Autowired
	protected MockMvc mockMvc;

	@Value("${jwt.http.request.header}")
	protected String tokenHeader;

	protected final UserDto TESTED_USER = new UserDto(1L, "tadam", "tadam12345");

	protected final ObjectMapper objectMapper = new ObjectMapper();
	private TokenDto token;

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
		MvcResult result = mockMvc
				.perform(
						post("/authenticate")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(TESTED_USER))
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.token").exists())
				.andReturn();
		token = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDto.class);
	}

	@SneakyThrows
	protected ResultActions performWithToken(final MockHttpServletRequestBuilder request) {
		request.header(tokenHeader, "Bearer " + token.getToken());
		return mockMvc.perform(request);
	}
}
