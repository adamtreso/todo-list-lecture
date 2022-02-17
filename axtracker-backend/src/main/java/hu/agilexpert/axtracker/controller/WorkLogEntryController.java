package hu.agilexpert.axtracker.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.agilexpert.axtracker.dto.WorkLogEntryDto;
import hu.agilexpert.axtracker.service.WorkLogEntryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WorkLogEntryController {

	@Autowired
	private WorkLogEntryService workLogEntryService;

	@GetMapping("users/{userId}/worklogentrys")
	public ResponseEntity<List<WorkLogEntryDto>> getAllWorkLogEntrys(@PathVariable final Long userId) {
		Optional<List<WorkLogEntryDto>> response = workLogEntryService.getAllWorkLogEntrys(userId);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("users/{userId}/worklogentrys/{id}")
	public ResponseEntity<Void> deleteWorkLogEntry(@PathVariable final Long userId, @PathVariable final long id) {
		boolean isDeleted = workLogEntryService.deleteWorkLogEntry(id);
		return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@PutMapping("users/{userId}/worklogentrys/{id}")
	public ResponseEntity<WorkLogEntryDto> updateWorkLogEntry(
			@PathVariable final Long userId,
			@PathVariable final long id,
			@RequestBody final WorkLogEntryDto workLogEntry) {
		workLogEntry.setId(id);
		Optional<WorkLogEntryDto> response = workLogEntryService.updateWorkLogEntry(workLogEntry);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping("users/{userId}/worklogentrys")
	public ResponseEntity<URI> createWorkLogEntry(@PathVariable final Long userId, @RequestBody final WorkLogEntryDto workLogEntry) {
		Optional<URI> response = workLogEntryService.createWorkLogEntry(userId, workLogEntry);
		return response.isPresent() ? ResponseEntity.created(response.get()).build() : ResponseEntity.badRequest().build();
	}

	@GetMapping("users/{userId}/worklogentrys/{id}")
	public ResponseEntity<WorkLogEntryDto> getWorkLogEntry(@PathVariable final Long userId, @PathVariable final long id) {
		Optional<WorkLogEntryDto> response = workLogEntryService.getWorkLogEntry(id);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}
}
