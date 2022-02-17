package hu.agilexpert.axtracker.test.worklogentry;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class WorkLogEntryControllerGetWorkLogEntryIt extends IntegrationTestBase {

	private final Long EXSISTING_ID = 1L;
	private final Long NONEXSISTING_ID = -10L;

	@Test
	@SneakyThrows
	public void testGivenExsistingUserIdThenWorkLogEntryRecievedIt() {
		// GIVEN
		UserDto authenticaedUser = authenticateUser(EXSISTING_USER);
		MockHttpServletRequestBuilder request =
				get("/users/" + authenticaedUser.getId() + "/worklogentrys/" + EXSISTING_ID).accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		MvcResult result = resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		MatcherAssert.assertThat(result.getResponse().getContentAsString(), sameJsonAsApproved());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingIdThenNotFoundRecievedIt() {
		// GIVEN
		UserDto authenticaedUser = authenticateUser(EXSISTING_USER);
		MockHttpServletRequestBuilder request =
				get("/users/" + authenticaedUser.getId() + "/worklogentrys/" + NONEXSISTING_ID).accept(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
	}
}
