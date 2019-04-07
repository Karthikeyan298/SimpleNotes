package com.wander.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The Class WebSecurityConfig.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    /** The user details service. */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Bcrypt password encoder.
     *
     * @return the bcrypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	

    	http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/**").permitAll()
    	.antMatchers(HttpMethod.POST, "/user/**").permitAll().
    	antMatchers(HttpMethod.GET, "/login").permitAll().
    	antMatchers("/css/**", "/js/**").permitAll()
    	.and().formLogin().loginPage("/login").defaultSuccessUrl("/welcome").and().
    	authorizeRequests().anyRequest().authenticated().and().
        cors().and().
//        TODO: Need to enable csrf headers and cors
        csrf().disable();
    }

    /**
     * Custom authentication manager.
     *
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    /**
     * Configure global.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}