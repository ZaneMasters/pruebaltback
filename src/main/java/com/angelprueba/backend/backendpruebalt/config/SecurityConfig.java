package com.angelprueba.backend.backendpruebalt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		
		UserDetails angel = User.builder().username("angel")
				.password("{noop}angel123")
				.roles("EXTERNO")
				.build();
		
		UserDetails alfredo = User.builder().username("admin")
				.password("{noop}admin")
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(angel, alfredo);
	}


    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests( configure ->{
			configure
            .requestMatchers(HttpMethod.GET,"/empresas", "/productos").hasAnyRole("ADMIN", "EXTERNO")
			.requestMatchers(HttpMethod.POST,"/empresas").hasRole("ADMIN")
			.requestMatchers(HttpMethod.PUT,"/empresas/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.DELETE,"/empresas/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.POST,"/productos").hasRole("ADMIN")
			.requestMatchers(HttpMethod.DELETE,"/productos/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.GET,"/productos/**").hasRole("ADMIN")
			.requestMatchers("/v1/authenticate","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll();
		});
		
		http.httpBasic(Customizer.withDefaults());
		
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
	}

}