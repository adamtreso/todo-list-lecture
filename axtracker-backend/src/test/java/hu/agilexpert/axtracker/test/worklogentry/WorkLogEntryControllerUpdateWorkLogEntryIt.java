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

import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.dto.WorkLogEntryDto;
import hu.agilexpert.axtracker.service.WorkLogEntryService;
import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class WorkLogEntryControllerUpdateWorkLogEntryIt extends IntegrationTestBase {

	@Autowired
	private WorkLogEntryService workLogEntryService;

	private final WorkLogEntryDto WORKLOGENTRY_TO_UPDATE = new WorkLogEntryDto(1L, new Date(1), new Date(1), "Chenged title");
	private final Long NON_EXSISTING_USER_ID = -10L;

	@Test
	@SneakyThrows
	public void testGivenExsistingUserIdThenWorkLogEntryRecievedAndDatabaseChangedIt() {
		// GIVEN
		UserDto authenticatedUser = authenticateUser(EXSISTING_USER);
		MockHttpServletRequestBuilder request = put("/users/" + authenticatedUser.getId() + "/worklogentrys/" + WORKLOGENTRY_TO_UPDATE.getId())
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(WORKLOGENTRY_TO_UPDATE))
				.contentType(MediaType.APPLICATION_JSON);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		MvcResult result = resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		MatcherAssert.assertThat(result.getResponse().getContentAsString(), sameJsonAsApproved().withUniqueId("response"));
		MatcherAssert
				.assertThat(workLogEntryService.getAllWorkLogEntrys(authenticatedUser.getId()), sameJsonAsApproved().withUniqueId("table"));
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingIdThenNotFoundRecievedAndDatabaseNotChangedIt() {
		// GIVEN
		UserDto authenticatedUser = authenticateUser(EXSISTING_USER);
		MockHttpServletRequestBuilder request = put("/users/" + authenticatedUser.getId() + "/worklogentrys/12345")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(WORKLOGENTRY_TO_UPDATE))
				.contentType(MediaType.APPLICATION_JSON);;
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
	}
}
