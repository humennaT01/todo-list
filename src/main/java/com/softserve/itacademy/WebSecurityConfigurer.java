package com.softserve.itacademy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableGlobalMethodSecurity(
        prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private WebAuthenticationFilter authenticationFilter;

    private WebAccessDeniedHandler webAccessDeniedHandler;

    @Autowired
    public void setAuthenticationFilter(WebAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Autowired
    public void setWebAccessDeniedHandler(WebAccessDeniedHandler webAccessDeniedHandler) {
        this.webAccessDeniedHandler = webAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/login-form").permitAll()
                .antMatchers("/users/create").permitAll()
                .anyRequest().authenticated();

        http.addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
                .authenticationEntryPoint(
                (request, response, authException) -> response.sendRedirect("/login-form"));
    }


    }

