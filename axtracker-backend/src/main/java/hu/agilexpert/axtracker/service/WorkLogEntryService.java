package hu.agilexpert.axtracker.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.agilexpert.axtracker.common.ExtendedConversionService;
import hu.agilexpert.axtracker.dto.WorkLogEntryDto;
import hu.agilexpert.axtracker.entity.User;
import hu.agilexpert.axtracker.entity.WorkLogEntry;
import hu.agilexpert.axtracker.repository.UserRepository;
import hu.agilexpert.axtracker.repository.WorkLogEntryRepository;

@Service
public class WorkLogEntryService {

	@Autowired
	private ExtendedConversionService extendedConvService;

	@Autowired
	private WorkLogEntryRepository workLogEntryRepos;

	@Autowired
	private UserRepository userRepos;

	public Optional<List<WorkLogEntryDto>> getAllWorkLogEntrys(final String username) {
		Optional<User> userResult = userRepos.findByUsername(username);
		if (userResult.isPresent()) {
			List<WorkLogEntry> result = workLogEntryRepos.findByUsernameOrderById(username);
			return Optional.of(extendedConvService.convert(result, WorkLogEntryDto.class));
		}
		return Optional.empty();
	}

	public boolean deleteWorkLogEntry(final String username, final long id) {
		Optional<WorkLogEntry> response = workLogEntryRepos.findOneByUsernameAndId(username, id);

		if (response.isPresent()) {
			workLogEntryRepos.deleteById(response.get().getId());
			return true;
		}
		return false;
	}

	public Optional<WorkLogEntryDto> updateWorkLogEntry(final String username, final long id, final WorkLogEntry workLogEntry) {
		Optional<WorkLogEntry> response = workLogEntryRepos.findOneByUsernameAndId(username, id);

		if (response.isPresent()) {
			workLogEntry.setId(response.get().getId());
			workLogEntry.setUsername(response.get().getUsername());
			WorkLogEntry updatedTodo = workLogEntryRepos.save(workLogEntry);
			return Optional.of(extendedConvService.convert(updatedTodo, WorkLogEntryDto.class));
		}
		return Optional.empty();
	}

	public Optional<URI> createWorkLogEntry(final String username, final WorkLogEntry workLogEntry) {
		Optional<User> userResult = userRepos.findByUsername(username);
		if (userResult.isPresent()) {
			workLogEntry.setId(null);
			WorkLogEntry savedWorkLogEntry = workLogEntryRepos.save(workLogEntry);
			return Optional.of(URI.create(String.format("users/%s/worklogentrys/%s", username, savedWorkLogEntry.getId())));
		}
		return Optional.empty();
	}

	public Optional<WorkLogEntryDto> getWorkLogEntryDto(final String username, final long id) {
		Optional<WorkLogEntry> resoult = workLogEntryRepos.findOneByUsernameAndId(username, id);
		if (resoult.isPresent()) {
			return Optional.of(extendedConvService.convert(resoult.get(), WorkLogEntryDto.class));
		}
		return Optional.empty();
	}
}
