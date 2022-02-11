package hu.agilexpert.axtracker.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import hu.agilexpert.axtracker.dto.WorkLogEntryDto;
import hu.agilexpert.axtracker.entity.WorkLogEntry;

@Component
public class WorkLogEntry2WorkLogEntryDto implements Converter<WorkLogEntry, WorkLogEntryDto> {

	@Override
	public WorkLogEntryDto convert(final WorkLogEntry source) {
		WorkLogEntryDto resoult = new WorkLogEntryDto();
		resoult.setId(source.getId());
		resoult.setUsername(source.getUsername());
		resoult.setDescription(source.getDescription());
		resoult.setTargetDate(source.getTargetDate());
		resoult.setDone(source.isDone());
		return resoult;
	}

}
