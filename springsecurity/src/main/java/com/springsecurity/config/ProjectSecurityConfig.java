package com.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.springsecurity.filter.AuthoritiesLoggingAfterFilter;
import com.springsecurity.filter.AuthoritiesLoggingAtFilter;
import com.springsecurity.filter.RequestValidationBeforeFilter;

@Configuration
public class ProjectSecurityConfig {
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{   
		
		//we use hasauthority methods when we want to give access to particular actions 
		// and we use roles when we want to give access to set of actions in spring security 
		http
		.addFilterBefore(new RequestValidationBeforeFilter(),BasicAuthenticationFilter.class)
		.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
		.addFilterAt(new AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class)
		.authorizeHttpRequests((authorizeHttpRequests) ->
 				{
						authorizeHttpRequests
					/*
					 * .requestMatchers("/testController/**").hasAuthority("getDetails()")
					 * .requestMatchers("/anotherTestController/**").hasAuthority("getAlldetails")
					 * .requestMatchers("/moreAnotherDetails/**").hasAuthority("getMoredetails")
					 */
						
						.requestMatchers("/testController/**").hasRole("ADMIN")
						.requestMatchers("/anotherTestController/**").hasRole("USER")
						.requestMatchers("/moreAnotherDetails/**").hasRole("USER")
						.requestMatchers("/demo1/**","/demo2/**","/h2-console/**","/register/**").permitAll();
							
				}
 					
 			)
		//this is custom filter added 
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
			/*
			 * http.csrf().disable(); http.headers().frameOptions().disable();
			 */
 		return http.build();
		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	//for creating immemory user and password 
	//@Bean
	/*
	 * public InMemoryUserDetailsManager userDetailsService() { UserDetails
	 * admin=User.withDefaultPasswordEncoder() .username("admin") .password("12345")
	 * .roles("admin") .build(); UserDetails user=User.withDefaultPasswordEncoder()
	 * .username("user") .password("12345") .roles("read") .build();
	 * 
	 * return new InMemoryUserDetailsManager(admin,user);
	 * 
	 * }
	 */
	
	
	//different method for creating user and password 
	
	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsService2() { UserDetails
	 * admin=User.withUsername("admin") .password("12345") .build(); UserDetails
	 * user=User.withUsername("user") .password("12345") .build();
	 * 
	 * return new InMemoryUserDetailsManager(admin,user);
	 * 
	 * }
	 */
	/*
	 * @Bean public UserDetailsService userDetailsService(DataSource datasource) {
	 * return new JdbcUserDetailsManager(datasource); }
	 */
	
	

	
	
	
	
	
	
	
	

}
