package com.adamtreso.rest.webservices.restfullwebservices.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adamtreso.rest.webservices.restfullwebservices.entity.User;
import com.adamtreso.rest.webservices.restfullwebservices.repository.UserRepository;
import com.adamtreso.rest.webservices.restfullwebservices.service.impl.jwtuserdetailsservice.JwtUserPrincipal;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepos;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> response = userRepos.findByUsername(username);

    if (response.isEmpty()) {
      throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
    }

    return new JwtUserPrincipal(response.get());
  }

}


