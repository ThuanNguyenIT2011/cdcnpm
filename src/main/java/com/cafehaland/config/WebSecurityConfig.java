package com.cafehaland.config;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cafehaland.Security.AccountDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http. csrf().disable().authorizeHttpRequests().anyRequest().authenticated()
				.and().formLogin()
					.loginPage("/login")
					.usernameParameter("email")
					.permitAll()
				.and().logout().permitAll()
				.and().rememberMe()
					.userDetailsService(userDetailsService())
					.key("nguyenvanthuan20112002@gmail.com")
					.tokenValiditySeconds(86400)
				.and().authenticationProvider(authenticationProvider());
		
		return http.build();
//		return http.authorizeHttpRequests().anyRequest().permitAll().and().build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new AccountDetailService();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/webfonts/**", "/fontawesome/**","/styles/**","/js/**", "/webjars/**");
    }
	
	@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
