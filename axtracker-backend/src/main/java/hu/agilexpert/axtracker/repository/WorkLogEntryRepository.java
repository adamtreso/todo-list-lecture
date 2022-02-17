package hu.agilexpert.axtracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.agilexpert.axtracker.entity.WorkLogEntry;

@Repository
public interface WorkLogEntryRepository extends JpaRepository<WorkLogEntry, Long> {

	List<WorkLogEntry> findByUserId(Long user_id);
}
