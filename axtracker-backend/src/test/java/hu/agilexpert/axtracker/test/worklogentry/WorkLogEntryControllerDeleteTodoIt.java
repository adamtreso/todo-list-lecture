package hu.agilexpert.axtracker.test.worklogentry;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.github.karsaig.approvalcrest.jupiter.MatcherAssert;

import hu.agilexpert.axtracker.repository.WorkLogEntryRepository;
import hu.agilexpert.axtracker.test.IntegrationTestBase;
import lombok.SneakyThrows;

public class WorkLogEntryControllerDeleteTodoIt extends IntegrationTestBase {

	@Autowired
	private WorkLogEntryRepository workLogEntryRepos;

	private final Long DELETED_ID = 1001L;

	@Test
	@SneakyThrows
	public void testGivenExsistingUserAndIdThenOkRecievedAndRowDeleted() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = delete("/users/" + TESTED_USER.getUsername() + "/worklogentrys/" + DELETED_ID);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isOk());
		MatcherAssert.assertThat(workLogEntryRepos.findByUsernameOrderById(TESTED_USER.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}

	@Test
	@SneakyThrows
	public void testGivenNonExsistingUserThenNotFoundRecieved() {
		// GIVEN
		authenticateTestedUser();
		MockHttpServletRequestBuilder request = delete("/user/nonexsistinguser/worklogentrys/" + DELETED_ID);
		// WHEN
		ResultActions resultActions = performWithToken(request);
		// THEN
		resultActions.andExpect(status().isNotFound());
		MatcherAssert.assertThat(workLogEntryRepos.findByUsernameOrderById(TESTED_USER.getUsername()), sameJsonAsApprovedIgnoringTargetDate());
	}
}
