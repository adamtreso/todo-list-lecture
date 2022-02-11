package hu.agilexpert.axtracker.service.impl.jwtuserdetailsservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hu.agilexpert.axtracker.entity.User;

public class JwtUserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 5155720064139820502L;
	private User user;
	  private final Collection<? extends GrantedAuthority> authorities;
	
	public JwtUserPrincipal(User user) {
		this.user = user;
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();  
		authorities.add(new SimpleGrantedAuthority("ROLE_USER_2"));
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override 
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
