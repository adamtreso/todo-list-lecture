package hu.agilexpert.axtracker.common;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 5379184699874309299L;

	public AuthenticationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
