package com.adamtreso.rest.webservices.restfullwebservices.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
	private Long id;
	private String username;
	private String description;
	private Date targetDate;
	private boolean isDone;
}
