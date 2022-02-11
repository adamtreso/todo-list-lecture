package hu.agilexpert.axtracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.agilexpert.axtracker.entity.WorkLogEntry;

@Repository
public interface WorkLogEntryRepository extends JpaRepository<WorkLogEntry, Long> {
	List<WorkLogEntry> findByUsernameOrderById(String username);

	Optional<WorkLogEntry> findOneByUsernameAndId(String username, Long id);
}
