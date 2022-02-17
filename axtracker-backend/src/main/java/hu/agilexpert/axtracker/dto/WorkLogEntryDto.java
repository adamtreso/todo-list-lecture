package hu.agilexpert.axtracker.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkLogEntryDto {
	private Long id;
	private Date startDate;
	private Date endDate;
	private String title;
}
