package hu.agilexpert.axtracker.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.entity.User;

@Component
public class User2UserDto implements Converter<User, UserDto>{

	@Override
	public UserDto convert(User source) {
		UserDto resoult = new UserDto();
		resoult.setId(source.getId());
		resoult.setUsername(source.getUsername());
		resoult.setPassword(source.getPassword());
		return resoult;
	}
	
}
