package com.springsecurity.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
    	JwtAuthenticationConverter jwtAuthenticationConverter= new JwtAuthenticationConverter();
    	
    	jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
    	
    	// Set up session management and CORS configuration
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				
				  .and() .cors().configurationSource(new CorsConfigurationSource() {
				  
				  @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
				  request) { CorsConfiguration config = new CorsConfiguration();
				  config.setAllowedOrigins(Arrays.asList("http://localhost:4200",
				  "http://localhost:8080"));
				  config.setAllowedMethods(Collections.singletonList("*"));
				  config.setAllowCredentials(true);
				  config.setAllowedHeaders(Collections.singletonList("*"));
				  config.setExposedHeaders(Arrays.asList("Authorization"));
				  config.setMaxAge(3600L); return config; } })
				 .and()
             .authorizeRequests(authorizeRequests -> authorizeRequests
            		//we want to use method level security using preAuthorize and postAuthorize and removing has role for test controller and putting it back as authenticated 
                //.requestMatchers("/testController/**").hasRole("ADMIN")
            		.requestMatchers("/testController/**").authenticated()
                .requestMatchers("/anotherTestController/**").hasRole("USER")
                .requestMatchers("/moreAnotherDetails/**").hasRole("USER")
                .requestMatchers("/demo1/**", "/demo2/**", "/h2-console/**", "/register/**").permitAll()
            )
            .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
  
    // For creating in-memory users and passwords (uncomment if needed)
    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    //     UserDetails admin = User.withDefaultPasswordEncoder()
    //         .username("admin")
    //         .password("12345")
    //         .roles("ADMIN")
    //         .build();
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //         .username("user")
    //         .password("12345")
    //         .roles("USER")
    //         .build();
    //     return new InMemoryUserDetailsManager(admin, user);
    // }

    // Another method for creating users and passwords
    // @Bean
    // public InMemoryUserDetailsManager userDetailsService2() {
    //     UserDetails admin = User.withUsername("admin")
    //         .password(passwordEncoder().encode("12345"))
    //         .roles("ADMIN")
    //         .build();
    //     UserDetails user = User.withUsername("user")
    //         .password(passwordEncoder().encode("12345"))
    //         .roles("USER")
    //         .build();
    //     return new InMemoryUserDetailsManager(admin, user);
    // }

    // Using JDBC to manage user details (uncomment and configure data source if needed)
    // @Bean
    // public UserDetailsService userDetailsService(DataSource dataSource) {
    //     return new JdbcUserDetailsManager(dataSource);
    // }
}















































/*
 * package com.springsecurity.config;
 * 
 * import java.util.Arrays; import java.util.Collections;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.Customizer; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.http.SessionCreationPolicy; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.security.web.SecurityFilterChain; import
 * org.springframework.security.web.authentication.www.
 * BasicAuthenticationFilter; import
 * org.springframework.web.cors.CorsConfiguration; import
 * org.springframework.web.cors.CorsConfigurationSource;
 * 
 * import com.springsecurity.filter.AuthoritiesLoggingAfterFilter; import
 * com.springsecurity.filter.AuthoritiesLoggingAtFilter; import
 * com.springsecurity.filter.JWTTokenGeneratorFilter; import
 * com.springsecurity.filter.JWTTokenValidatorFilter; import
 * com.springsecurity.filter.RequestValidationBeforeFilter;
 * 
 * import jakarta.servlet.http.HttpServletRequest;
 * 
 * @Configuration public class ProjectSecurityConfig {
 * 
 * 
 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
 * Exception {
 * 
 * //we use hasauthority methods when we want to give access to particular
 * actions // and we use roles when we want to give access to set of actions in
 * spring security http
 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
 * and() .cors().configurationSource(new CorsConfigurationSource() {
 * 
 * 
 * @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
 * request) { CorsConfiguration config = new CorsConfiguration();
 * config.setAllowedOrigins(Arrays.asList("http://localhost:4200",
 * "http://localhost:8080"));
 * config.setAllowedMethods(Collections.singletonList("*"));
 * config.setAllowCredentials(true);
 * config.setAllowedHeaders(Collections.singletonList("*"));
 * config.setExposedHeaders(Arrays.asList("Authorization"));
 * config.setMaxAge(3600L); return config; } }).and() .addFilterBefore(new
 * RequestValidationBeforeFilter(),BasicAuthenticationFilter.class)
 * .addFilterAfter(new AuthoritiesLoggingAfterFilter(),
 * BasicAuthenticationFilter.class) .addFilterAt(new
 * AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class)
 * .addFilterAfter(new JWTTokenGeneratorFilter(),
 * BasicAuthenticationFilter.class) .addFilterBefore(new
 * JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
 * .authorizeHttpRequests((authorizeHttpRequests) -> { authorizeHttpRequests
 * 
 * .requestMatchers("/testController/**").hasAuthority("getDetails()")
 * .requestMatchers("/anotherTestController/**").hasAuthority("getAlldetails")
 * .requestMatchers("/moreAnotherDetails/**").hasAuthority("getMoredetails")
 * 
 * 
 * .requestMatchers("/testController/**").hasRole("ADMIN")
 * .requestMatchers("/anotherTestController/**").hasRole("USER")
 * .requestMatchers("/moreAnotherDetails/**").hasRole("USER")
 * .requestMatchers("/demo1/**","/demo2/**","/h2-console/**","/register/**").
 * permitAll();
 * 
 * }
 * 
 * ) //this is custom filter added .formLogin(Customizer.withDefaults())
 * .httpBasic(Customizer.withDefaults());
 * 
 * http.csrf().disable(); http.headers().frameOptions().disable();
 * 
 * return http.build();
 * 
 * }
 * 
 * 
 * @Bean public PasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * //for creating immemory user and password //@Bean
 * 
 * public InMemoryUserDetailsManager userDetailsService() { UserDetails
 * admin=User.withDefaultPasswordEncoder() .username("admin") .password("12345")
 * .roles("admin") .build(); UserDetails user=User.withDefaultPasswordEncoder()
 * .username("user") .password("12345") .roles("read") .build();
 * 
 * return new InMemoryUserDetailsManager(admin,user);
 * 
 * }
 * 
 * 
 * 
 * //different method for creating user and password
 * 
 * 
 * @Bean public InMemoryUserDetailsManager userDetailsService2() { UserDetails
 * admin=User.withUsername("admin") .password("12345") .build(); UserDetails
 * user=User.withUsername("user") .password("12345") .build();
 * 
 * return new InMemoryUserDetailsManager(admin,user);
 * 
 * }
 * 
 * 
 * @Bean public UserDetailsService userDetailsService(DataSource datasource) {
 * return new JdbcUserDetailsManager(datasource); }
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * }
 */