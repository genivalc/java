package com.genival.pocredesocial.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/user")
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
