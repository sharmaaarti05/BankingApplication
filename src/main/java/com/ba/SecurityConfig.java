
package com.ba;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().requestMatchers("/admin/**").hasAnyRole("admin")
				.requestMatchers("/customer/**").hasAnyRole("customer").requestMatchers("/**").anonymous().anyRequest()
				.authenticated().and().httpBasic().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		//http.headers().frameOptions().disable();
		return http.build();

	}

	@Bean
	public InMemoryUserDetailsManager createUserDetailManager() {
		UserDetails ud1 =  createNewUser("admin","admin");
		UserDetails ud2 =  createNewUser("customer","customer");
		return new InMemoryUserDetailsManager(ud1, ud2);
		

	}
	
	public UserDetails createNewUser(String userName , String password) {
		Function<String , String> passwordEncode = input-> passowordEncoder().encode(input);
		return User.builder().passwordEncoder(passwordEncode)
				.username(userName).password(password).roles("admin", "customer").build();
		
	}

	
	@Bean
	public PasswordEncoder passowordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
