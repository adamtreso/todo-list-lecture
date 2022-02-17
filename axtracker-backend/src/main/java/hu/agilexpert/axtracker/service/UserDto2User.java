package hu.agilexpert.axtracker.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.entity.User;

@Component
public class UserDto2User implements Converter<UserDto, User> {

	@Override
	public User convert(final UserDto source) {
		User resoult = new User();
		resoult.setId(source.getId());
		resoult.setUsername(source.getUsername());
		resoult.setWorkLogEntrys(null);
		return resoult;
	}

}
