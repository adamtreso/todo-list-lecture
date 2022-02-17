package hu.agilexpert.axtracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.agilexpert.axtracker.common.ExtendedConversionService;
import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.entity.User;
import hu.agilexpert.axtracker.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private ExtendedConversionService extendedConvService;

	@Autowired
	private UserRepository userRepos;

	public Optional<UserDto> getUser(final String username) {
		Optional<User> result = userRepos.findByUsername(username);
		if (result.isPresent()) {
			return Optional.of(extendedConvService.convert(result.get(), UserDto.class));
		}
		return Optional.empty();
	}
}
