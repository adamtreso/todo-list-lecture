package hu.agilexpert.axtracker.test.worklogentry;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.service.WorkLogEntryService;
import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class WorkLogEntryControllerDeleteWorkLogEntryIt extends IntegrationTestBase {

	@Autowired
	private WorkLogEntryService workLogEntryService;

	private final Long DELETED_ID = 1L;
	private final Long NOT_EXSISTING_ID = -10L;

	@Test
	@SneakyThrows
	public void testGivenExsistingUserIdAndIdThenOkRecievedAndRowDeletedIt() {
		// GIVEN
		UserDto authenticatedUser = authenticateUser(EXSISTING_USER);
		MockHttpServletRequestBuilder request = delete("/users/" + authenticatedUser.getId() + "/worklogentrys/" + DELETED_ID);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isOk());
		MatcherAssert.assertThat(workLogEntryService.getAllWorkLogEntrys(authenticatedUser.getId()), sameJsonAsApproved());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingIdThenNotFoundRecievedIt() {
		// GIVEN
		UserDto authenticatedUser = authenticateUser(EXSISTING_USER);
		MockHttpServletRequestBuilder request = delete("/users/" + authenticatedUser.getId() + "/worklogentrys/" + NOT_EXSISTING_ID);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
	}
}
