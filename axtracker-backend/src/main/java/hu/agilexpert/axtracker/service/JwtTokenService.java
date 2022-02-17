package hu.agilexpert.axtracker.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.agilexpert.axtracker.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

@Service
public class JwtTokenService {

	private final String ISSUER = "AgileXpert Kft. - AX Tracker Backend";

	private Clock clock = DefaultClock.INSTANCE;

	@Value("${jwt.signing.key.secret}")
	private String SECRET;

	@Value("${jwt.token.expiration.in.seconds}")
	private Long EXPIRATION_TIME;

	@Autowired
	private ObjectMapper objectMapper;

	public String generateToken(final UserDto user) {
		final Date createdDate = clock.now();

		final Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("user", user);

		return Jwts
				.builder()
				.setClaims(claims)
				.setIssuer(ISSUER)
				.setSubject(user.getUsername())
				.setIssuedAt(createdDate)
				.setExpiration(calculateIdExpirationDate(createdDate))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	public Boolean canBeRefreshed(final String token) {
		return !isTokenExpired(getClaims(token));
	}

	public String refreshToken(final String token) {
		final Date createdDate = clock.now();

		final Claims claims = getClaims(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(calculateIdExpirationDate(createdDate));

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	public Boolean isValidToken(final String token, final UserDto user) {
		final Claims claims = getClaims(token);
		final UserDto tokenUser = objectMapper.convertValue(claims.get("user", LinkedHashMap.class), UserDto.class);
		return !isTokenExpired(claims) && tokenUser.equals(user);
	}

	public String getSubject(final String token) {
		return getClaims(token).getSubject();
	}

	private Date calculateIdExpirationDate(final Date createdDate) {
		return new Date(createdDate.getTime() + EXPIRATION_TIME * 1000);
	}

	private Claims getClaims(final String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(final Claims claims) {
		return claims.getExpiration().before(clock.now());
	}
}
