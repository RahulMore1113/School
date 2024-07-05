package com.rahul.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rahul.model.Person;
import com.rahul.model.Roles;
import com.rahul.repo.PersonRepo;

@Component
public class EazySchoolUsernamePwdAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PersonRepo repo;

	@Autowired
	private PasswordEncoder encoder;

	private List<GrantedAuthority> getGrantedAuthorities(Roles roles) {

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()));

		return grantedAuthorities;

	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String email = authentication.getName();
		String pwd = authentication.getCredentials().toString();

		Person person = repo.readByEmail(email);

		if (person != null && person.getPersonId() > 0 && encoder.matches(pwd, person.getPwd()))
			return new UsernamePasswordAuthenticationToken(email, null, getGrantedAuthorities(person.getRole()));
		else
			throw new BadCredentialsException("Invalid Credentials!");

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
