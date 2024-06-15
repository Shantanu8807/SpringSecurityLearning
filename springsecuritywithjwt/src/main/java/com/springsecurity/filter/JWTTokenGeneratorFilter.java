package com.springsecurity.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication!=null)
		{
			SecretKey key = Keys.hmacShaKeyFor("asdasdasdasdaskdbjhag7347612834yghjfsdajt6fit215uyerjwgtii62156rtfesadytr871r2feasdyf56r1823feyatfdfds".getBytes(StandardCharsets.UTF_8));
			String jwt= Jwts.builder()
	                .setIssuer("shantanu")
	                .setSubject("Jwt Token")
	                .claim("username", authentication.getName())  // Corrected "usernae" to "username"
	                .claim("authorities", populateAuthorities(authentication.getAuthorities()))
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 300000))  // Corrected expiration setting
	                .signWith(key)
	                .compact();
			response.setHeader("Authorization", jwt);
			
		}
		filterChain.doFilter(request, response);
		
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
	{
		return !request.getServletPath().equals("/login");
	}
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection)
	{
		Set<String> authoritiesSet= new HashSet<>();
		for(GrantedAuthority authority:collection)
		{
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);
		
	}

}
