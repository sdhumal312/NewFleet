package org.fleetopgroup.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@ComponentScan(basePackages = { "org.fleetopgroup.security" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

	public SecSecurityConfig() {
		super();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/s_security_fleetop*","/contactus*","/getUserDetailsFromCache*","/resendOTPValidationCode*", "/login*", "/logout*","/otpvalidate*","/validateOtp*", "/badUser*", "/emailError*", "/getVehicleGPS*",
						"/resources/**")
				.permitAll().antMatchers("/invalidSession*").anonymous().anyRequest().authenticated().and().formLogin()
				.loginPage("/login.html").loginProcessingUrl("/s_security_fleetop").defaultSuccessUrl("/open.html")
				.failureUrl("/login.html?error=true").successHandler(myAuthenticationSuccessHandler)
				.usernameParameter("j_username").passwordParameter("j_password").permitAll().and().sessionManagement()
				.invalidSessionUrl("/invalidSession.html").sessionFixation().none().and().logout()
				.invalidateHttpSession(false).logoutUrl("/fleetop_logout")
				.logoutSuccessUrl("/logout.html?logSucc=true").deleteCookies("JSESSIONID").permitAll();
	}

	// beans

	@Bean
	public DaoAuthenticationProvider authProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

}