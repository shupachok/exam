package com.supachok.exam.student.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.supachok.exam.student.service.StudentService;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	private StudentService studentService;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private Environment environment;
	
	public WebSecurity(StudentService studentService
			,BCryptPasswordEncoder bCryptPasswordEncoder,Environment environment) {
		this.studentService = studentService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
	}
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = 
				http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder.userDetailsService(studentService)
		.passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
        AuthenticationFilter authenticationFilter = 
        		new AuthenticationFilter(authenticationManager,studentService,environment);
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
       
        http.csrf().disable();
        
        http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET,"/students/**").permitAll()
		.requestMatchers(HttpMethod.POST, "/students").access(
				new WebExpressionAuthorizationManager("hasIpAddress('"+environment.getProperty("gateway.ip")+"')"))
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
        .and()
        .addFilter(authenticationFilter)
        .authenticationManager(authenticationManager)
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.headers().frameOptions().disable();
		
		return http.build();
		
	}
	
//	@Bean
//	StrictHttpFirewall httpFirewall() {
//	    StrictHttpFirewall firewall = new StrictHttpFirewall();
//        firewall.setAllowedHostnames(hostName -> hostName
//        		.equals(environment.getProperty("gateway.ip")));
//	    return firewall;
//	}

}
