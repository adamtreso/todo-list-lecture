package com.adamtreso.rest.webservices.restfullwebservices.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adamtreso.rest.webservices.restfullwebservices.entity.User;
import com.adamtreso.rest.webservices.restfullwebservices.service.impl.jwtuserdetailsservice.JwtUserPrincipal;
import com.adamtreso.rest.webservices.restfullwebservices.test.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepos;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		Optional<User> response = userRepos.findByUsername(username);

		if (!response.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return new JwtUserPrincipal(response.get());
	}

}
