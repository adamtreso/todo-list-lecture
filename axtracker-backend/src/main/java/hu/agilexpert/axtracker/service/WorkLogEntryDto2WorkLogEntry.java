package hu.agilexpert.axtracker.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import hu.agilexpert.axtracker.dto.WorkLogEntryDto;
import hu.agilexpert.axtracker.entity.WorkLogEntry;

@Component
public class WorkLogEntryDto2WorkLogEntry implements Converter<WorkLogEntryDto, WorkLogEntry> {

	@Override
	public WorkLogEntry convert(final WorkLogEntryDto source) {
		WorkLogEntry resoult = new WorkLogEntry();
		resoult.setId(source.getId());
		resoult.setUser(null);
		resoult.setStartDate(source.getStartDate());
		resoult.setEndDate(source.getEndDate());
		resoult.setTitle(source.getTitle());
		return resoult;
	}

}
