package hu.agilexpert.axtracker.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.karsaig.approvalcrest.jupiter.matcher.Matchers;
import com.github.karsaig.approvalcrest.matcher.GsonConfiguration;
import com.github.karsaig.approvalcrest.matcher.JsonMatcher;

import hu.agilexpert.axtracker.RestfullWebServicesApplication;
import hu.agilexpert.axtracker.dto.JwtAuthenticationRequestDto;
import hu.agilexpert.axtracker.dto.JwtTokenDto;
import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.service.JwtTokenService;
import hu.agilexpert.axtracker.service.UserService;
import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfullWebServicesApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public abstract class IntegrationTestBase {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected JwtTokenService jwtTokenService;

	@Autowired
	protected UserService userService;

	@Value("${jwt.http.request.header}")
	protected String tokenHeader;

	protected final JwtAuthenticationRequestDto EXSISTING_USER = new JwtAuthenticationRequestDto("gipszjakab", "a");

	private String token;

	protected JsonMatcher<Object> sameJsonAsApproved() {
		GsonConfiguration gsonConfiguration = new GsonConfiguration();
		gsonConfiguration.addTypeAdapterFactory(DateTypeAdapterForJsonMatcher.FACTORY);
		return Matchers.sameJsonAsApproved().withGsonConfiguration(gsonConfiguration);
	}

	@SneakyThrows
	protected UserDto authenticateUser(final JwtAuthenticationRequestDto requestData) {
		MockHttpServletRequestBuilder request = post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(EXSISTING_USER))
				.accept(MediaType.APPLICATION_JSON);
		ResultActions resultActions = mockMvc.perform(request);
		MvcResult result = resultActions
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.token").exists())
				.andReturn();
		token = objectMapper.readValue(result.getResponse().getContentAsString(), JwtTokenDto.class).getToken();
		return userService.getUser(jwtTokenService.getSubject(token)).get();
	}

	@SneakyThrows
	protected ResultActions performWithToken(final MockHttpServletRequestBuilder request) {
		request.header(tokenHeader, "Bearer " + token);
		return mockMvc.perform(request);
	}
}
