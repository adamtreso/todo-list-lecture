package com.adamtreso.rest.webservices.restfullwebservices.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.adamtreso.rest.webservices.restfullwebservices.dto.TodoDto;
import com.adamtreso.rest.webservices.restfullwebservices.entity.Todo;

@Component
public class Todo2TodoDto implements Converter<Todo, TodoDto> {

	@Override
	public TodoDto convert(final Todo source) {
		TodoDto resoult = new TodoDto();
		resoult.setId(source.getId());
		resoult.setUsername(source.getUsername());
		resoult.setDescription(source.getDescription());
		resoult.setTargetDate(source.getTargetDate());
		resoult.setDone(source.isDone());
		return resoult;
	}

}
