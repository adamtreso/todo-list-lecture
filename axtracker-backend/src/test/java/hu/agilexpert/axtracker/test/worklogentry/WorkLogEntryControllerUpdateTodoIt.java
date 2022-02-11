package hu.agilexpert.axtracker.test.worklogentry;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import hu.agilexpert.axtracker.dto.WorkLogEntryDto;
import hu.agilexpert.axtracker.repository.WorkLogEntryRepository;
import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class WorkLogEntryControllerUpdateTodoIt extends IntegrationTestBase {

	@Autowired
	private WorkLogEntryRepository workLogEntryRepos;

	private final WorkLogEntryDto WORKLOGENTRY_TO_UPDATE = new WorkLogEntryDto(1001L, TESTED_USER.getUsername(), "Changed value", new Date(), true);

	@Test
	@SneakyThrows
	public void testGivenExsistingUserThenTodoIsRecievedAndDatabaseChanged() throws JsonProcessingException {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = put("/users/" + WORKLOGENTRY_TO_UPDATE.getUsername() + "/worklogentrys/" + WORKLOGENTRY_TO_UPDATE.getId())
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(WORKLOGENTRY_TO_UPDATE))
				.contentType(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		MvcResult result = resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		MatcherAssert.assertThat(result.getResponse().getContentAsString(), sameJsonAsApprovedIgnoringTargetDate().withUniqueId("response"));
		MatcherAssert
				.assertThat(
						workLogEntryRepos.findByUsernameOrderById(WORKLOGENTRY_TO_UPDATE.getUsername()),
						sameJsonAsApprovedIgnoringTargetDate().withUniqueId("table"));
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenNotFoundRecievedAndDatabaseNotChanged() throws JsonProcessingException {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = put("/users/nonexsistinguser/worklogentrys/" + WORKLOGENTRY_TO_UPDATE.getId())
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(WORKLOGENTRY_TO_UPDATE))
				.contentType(MediaType.APPLICATION_JSON);;
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
		MatcherAssert.assertThat(workLogEntryRepos.findByUsernameOrderById(WORKLOGENTRY_TO_UPDATE.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingIdThenNotFoundRecievedAndDatabaseNotChanged() throws JsonProcessingException {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = put("/users/" + WORKLOGENTRY_TO_UPDATE.getUsername() + "/worklogentrys/12345")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(WORKLOGENTRY_TO_UPDATE))
				.contentType(MediaType.APPLICATION_JSON);;
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
		MatcherAssert.assertThat(workLogEntryRepos.findByUsernameOrderById(WORKLOGENTRY_TO_UPDATE.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}
}
