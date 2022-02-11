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
import hu.agilexpert.axtracker.entity.WorkLogEntry;
import hu.agilexpert.axtracker.service.WorkLogEntryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WorkLogEntryController {

	@Autowired
	private WorkLogEntryService workLogEntryService;

	@GetMapping("users/{username}/worklogentrys")
	public ResponseEntity<List<WorkLogEntryDto>> getAllWorkLogEntrys(@PathVariable final String username) {
		Optional<List<WorkLogEntryDto>> response = workLogEntryService.getAllWorkLogEntrys(username);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("users/{username}/worklogentrys/{id}")
	public ResponseEntity<Void> deleteWorkLogEntry(@PathVariable final String username, @PathVariable final long id) {
		boolean isDeleted = workLogEntryService.deleteWorkLogEntry(username, id);
		return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@PutMapping("users/{username}/worklogentrys/{id}")
	public ResponseEntity<WorkLogEntryDto> updateWorkLogEntry(
			@PathVariable final String username,
			@PathVariable final long id,
			@RequestBody final WorkLogEntry WorkLogEntry) {
		Optional<WorkLogEntryDto> response = workLogEntryService.updateWorkLogEntry(username, id, WorkLogEntry);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping("users/{username}/worklogentrys")
	public ResponseEntity<URI> createWorkLogEntry(@PathVariable final String username, @RequestBody final WorkLogEntry workLogEntry) {
		Optional<URI> response = workLogEntryService.createWorkLogEntry(username, workLogEntry);
		return response.isPresent() ? ResponseEntity.created(response.get()).build() : ResponseEntity.notFound().build();
	}

	@GetMapping("users/{username}/worklogentrys/{id}")
	public ResponseEntity<WorkLogEntryDto> getWorkLogEntry(@PathVariable final String username, @PathVariable final long id) {
		Optional<WorkLogEntryDto> response = workLogEntryService.getWorkLogEntryDto(username, id);
		return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
	}
}
