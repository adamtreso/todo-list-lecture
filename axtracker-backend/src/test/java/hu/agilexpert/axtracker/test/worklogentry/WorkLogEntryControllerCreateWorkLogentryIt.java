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

import hu.agilexpert.axtracker.dto.WorkLogEntryDto;
import hu.agilexpert.axtracker.repository.WorkLogEntryRepository;
import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class WorkLogEntryControllerCreateWorkLogentryIt extends IntegrationTestBase {

	@Autowired
	private WorkLogEntryRepository workLogEntryRepos;

	private final WorkLogEntryDto TODO_TO_CREATE = new WorkLogEntryDto(-1L, TESTED_USER.getUsername(), "New value", new Date(), true);
	private final WorkLogEntryDto TODO_TO_CREATE_NONEXSISTING = new WorkLogEntryDto(-1L, "nonexsistinguser", "Changed value", new Date(), true);

	@Test
	@SneakyThrows
	public void testGivenExsistingUserThenTodosIsRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = post("/users/" + TODO_TO_CREATE.getUsername() + "/worklogentrys/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TODO_TO_CREATE));
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isCreated()).andExpect(redirectedUrlPattern("users/" + TODO_TO_CREATE.getUsername() + "/worklogentrys/*"));
		MatcherAssert.assertThat(workLogEntryRepos.findByUsernameOrderById(TODO_TO_CREATE.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenNotFoundRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = post("/users/" + TODO_TO_CREATE_NONEXSISTING.getUsername() + "/worklogentrys/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TODO_TO_CREATE_NONEXSISTING));
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
	}
}
