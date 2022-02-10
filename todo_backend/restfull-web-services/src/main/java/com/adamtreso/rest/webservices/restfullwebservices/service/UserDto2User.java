package com.adamtreso.rest.webservices.restfullwebservices.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.adamtreso.rest.webservices.restfullwebservices.dto.UserDto;
import com.adamtreso.rest.webservices.restfullwebservices.entity.User;

@Component
public class UserDto2User implements Converter<UserDto, User>{

	@Override
	public User convert(UserDto source) {
		User resoult = new User();
		resoult.setId(source.getId());
		resoult.setUsername(source.getUsername());
		resoult.setPassword(source.getPassword());
		return resoult;
	}
	
}
