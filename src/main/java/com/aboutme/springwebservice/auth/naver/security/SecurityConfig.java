package com.aboutme.springwebservice.auth.naver.security;

import com.aboutme.springwebservice.auth.naver.security.filter.JwtTokenFilterConfigurer;
import com.aboutme.springwebservice.auth.naver.security.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.WeakHashMap;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //No session will be created by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("auth/signup").permitAll()
                    .antMatchers("auth/signin").permitAll()
                    .antMatchers("/auth/test").permitAll()
                    .antMatchers("/auth/refresh").authenticated()
                    .antMatchers("/v1/**").authenticated();

        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }
}
