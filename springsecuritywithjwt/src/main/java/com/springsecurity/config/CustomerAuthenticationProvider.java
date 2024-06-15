package com.springsecurity.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.springsecurity.model.Authority;
import com.springsecurity.model.Customer;
import com.springsecurity.repo.CustomerRepo;


@Component
public class CustomerAuthenticationProvider implements AuthenticationProvider  {
    
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	


	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username=authentication.getName();
		
		String password= authentication.getCredentials().toString();
		
		List<Customer> customer= customerRepo.findByEmail(username);
		
		System.out.println(customer.toString());
		if(customer.size()>0)
		{
			if(passwordEncoder.matches(password, customer.get(0).getPassword()))
			{
				/*
				 * List<GrantedAuthority> authorities=new ArrayList<>(); authorities.add(new
				 * SimpleGrantedAuthority(customer.get(0).getRole()));
				 */
				return new UsernamePasswordAuthenticationToken(username,password, getGrantedAuthorities(customer.get(0).getAuthorities()));
			}
			else
			{
				throw new BadCredentialsException("Invalid Password");
			}
		}
		else
		{
			throw new BadCredentialsException("No User Registered With Details");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	
	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities)
	{
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(Authority authority:authorities)
		{
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}
		
		return grantedAuthorities;
	}

}
