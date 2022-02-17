package hu.agilexpert.axtracker.controller;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.agilexpert.axtracker.common.AuthenticationException;
import hu.agilexpert.axtracker.dto.JwtAuthenticationRequestDto;
import hu.agilexpert.axtracker.dto.JwtTokenDto;
import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.service.JwtTokenService;
import hu.agilexpert.axtracker.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationRestController {

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenService jwtTokenService;

	@RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtAuthenticationRequestDto jwtRequest)
			throws AuthenticationException {

		authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		final Optional<UserDto> user = userService.getUser(jwtRequest.getUsername());
		if (user.isPresent()) {
			final String token = jwtTokenService.generateToken(user.get());
			return ResponseEntity.ok(new JwtTokenDto(token));
		}
		return ResponseEntity.badRequest().build();
	}

	@RequestMapping(value = "${jwt.refresh.token.uri}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(final HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);

		if (jwtTokenService.canBeRefreshed(token)) {
			String refreshedToken = jwtTokenService.refreshToken(token);
			return ResponseEntity.ok(new JwtTokenDto(refreshedToken));
		}
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler({AuthenticationException.class})
	public ResponseEntity<String> handleAuthenticationException(final AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	private void authenticate(final String username, final String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
			authenticationManager.authenticate(authentication);
		} catch (DisabledException e) {
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}
}
