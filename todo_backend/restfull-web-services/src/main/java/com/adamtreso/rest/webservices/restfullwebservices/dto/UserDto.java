package com.adamtreso.rest.webservices.restfullwebservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String username;
	private String password;
}
