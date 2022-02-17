package hu.agilexpert.axtracker.test.worklogentry;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.dto.WorkLogEntryDto;
import hu.agilexpert.axtracker.service.WorkLogEntryService;
import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class WorkLogEntryControllerCreateWorkLogentryIt extends IntegrationTestBase {

	@Autowired
	private WorkLogEntryService workLogEntryService;

	private final Long NON_EXSISTING_USER_ID = -10L;

	private final WorkLogEntryDto WORKLOGENTRY_TO_CREATE = new WorkLogEntryDto(-1L, new Date(1), new Date(1), "title");

	@Test
	@SneakyThrows
	public void testGivenExsistingUserWithNewWorkLogEntryThenSavedWorkLogEntryRecievedAndDatabaseChangedIt() {
		// GIVEN
		UserDto authenticatedUser = authenticateUser(EXSISTING_USER);
		MockHttpServletRequestBuilder request = post("/users/" + authenticatedUser.getId() + "/worklogentrys/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(WORKLOGENTRY_TO_CREATE));
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions
				.andExpect(status().isCreated())
				.andExpect(redirectedUrlPattern("users/" + authenticatedUser.getId() + "/worklogentrys/*"));
		MatcherAssert.assertThat(workLogEntryService.getAllWorkLogEntrys(authenticatedUser.getId()), sameJsonAsApproved());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUsernameThenNotFoundRecievedIt() {
		// GIVEN
		UserDto authenticatedUser = authenticateUser(EXSISTING_USER);
		MockHttpServletRequestBuilder request = post("/users/" + NON_EXSISTING_USER_ID + "/worklogentrys/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(WORKLOGENTRY_TO_CREATE));
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isBadRequest());
	}
}
