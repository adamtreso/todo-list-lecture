package hu.agilexpert.axtracker.common.jwt;
public class AuthenticationException extends RuntimeException {
	
	private static final long serialVersionUID = 5379184699874309299L;

	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

