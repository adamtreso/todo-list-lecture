package hu.agilexpert.axtracker.service.impl.jwtuserdetailsservice;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import hu.agilexpert.axtracker.dto.UserDto;
import hu.agilexpert.axtracker.service.JwtTokenService;
import hu.agilexpert.axtracker.service.UserService;

@Component
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JwtTokenService jwtTokenService;
	@Autowired
	private UserService userService;

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
			throws ServletException, IOException {
		logger.debug("Authentication Request For '{}'", request.getRequestURL());

		final String requestTokenHeader = request.getHeader(tokenHeader);

		UserDto user = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			String username = jwtTokenService.getSubject(jwtToken);
			if (username != null) {
				Optional<UserDto> userResult = userService.getUser(username);
				if (userResult.isPresent()) {
					user = userResult.get();
				} else {
					logger.error("JWT_TOKEN_UNABLE_TO_GET_USER_FROM_TOKEN");
				}
			} else {
				logger.error("NOT_EXSISTING_USER");
			}
		} else {
			logger.warn("JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING");
		}

		logger.debug("JWT_TOKEN_USERNAME_VALUE '{}'", user);
		if (user != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtTokenService.isValidToken(jwtToken, user)) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}

		chain.doFilter(request, response);
	}
}
