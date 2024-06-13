package com.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.authorizeHttpRequests((authorizeHttpRequests) ->
 				{
						authorizeHttpRequests
							.requestMatchers("/testController/**","/anotherTestController/**","/moreAnotherDetails/**").authenticated()
							.requestMatchers("/demo1/**","/demo2/**","/h2-console/**","/register/**").permitAll();
							
				}
 					
 			)
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
		http.csrf().disable();
		http.headers().frameOptions().disable();
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
