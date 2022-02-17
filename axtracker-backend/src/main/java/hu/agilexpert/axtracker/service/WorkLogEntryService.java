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

	public Optional<List<WorkLogEntryDto>> getAllWorkLogEntrys(final Long userId) {
		List<WorkLogEntry> result = workLogEntryRepos.findByUserId(userId);
		if (result.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(extendedConvService.convert(result, WorkLogEntryDto.class));
	}

	public boolean deleteWorkLogEntry(final long id) {
		Optional<WorkLogEntry> result = workLogEntryRepos.findById(id);
		if (result.isPresent()) {
			workLogEntryRepos.deleteById(id);
			return true;
		}
		return false;
	}

	public Optional<WorkLogEntryDto> updateWorkLogEntry(final WorkLogEntryDto workLogEntryDto) {
		Optional<WorkLogEntry> workLogEntryResult = workLogEntryRepos.findById(workLogEntryDto.getId());
		if (workLogEntryResult.isPresent()) {
			WorkLogEntry workLogEntry = extendedConvService.convert(workLogEntryDto, WorkLogEntry.class);
			workLogEntry.setUser(workLogEntryResult.get().getUser());
			WorkLogEntry updatedTodo = workLogEntryRepos.save(workLogEntry);
			return Optional.of(extendedConvService.convert(updatedTodo, WorkLogEntryDto.class));
		}
		return Optional.empty();
	}

	public Optional<URI> createWorkLogEntry(final Long userId, final WorkLogEntryDto workLogEntryDto) {
		Optional<User> userResult = userRepos.findById(userId);
		if (userResult.isPresent()) {
			WorkLogEntry workLogEntry = extendedConvService.convert(workLogEntryDto, WorkLogEntry.class);
			workLogEntry.setUser(userResult.get());
			workLogEntry.setId(null);
			WorkLogEntry savedWorkLogEntry = workLogEntryRepos.save(workLogEntry);
			return Optional.of(URI.create(String.format("users/%s/worklogentrys/%s", userId, savedWorkLogEntry.getId())));
		}
		return Optional.empty();
	}

	public Optional<WorkLogEntryDto> getWorkLogEntry(final long id) {
		Optional<WorkLogEntry> resoult = workLogEntryRepos.findById(id);
		if (resoult.isPresent()) {
			return Optional.of(extendedConvService.convert(resoult.get(), WorkLogEntryDto.class));
		}
		return Optional.empty();
	}
}
