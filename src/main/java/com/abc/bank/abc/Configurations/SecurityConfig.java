package com.abc.bank.abc.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");

    }

    // Authorization : Role -> Access
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());

        http.authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagger-ui.html").authenticated()
                .antMatchers("/customer-management/*", "/customer-management/*/*").access("hasAuthority('MANAGER')")
                .antMatchers("/counters/*", "/counters/*/*").access("hasAnyAuthority('MANAGER','COUNTER_OPERATOR')")
                .antMatchers("/branches/*", "/branches/*/*").access("hasAnyAuthority('MANAGER')")
                .antMatchers("/employees/*", "/employees/*/*").access("hasAnyAuthority('ADMIN')")
                .antMatchers("/multipleCounterServices/*", "/multipleCounterServices/*/*").access("hasAnyAuthority('MANAGER')")
                .antMatchers("/services/*", "/services/*/*").access("hasAnyAuthority('MANAGER')")
                .antMatchers("/tokens/*", "/tokens/*/*").access("hasAnyAuthority('MANAGER','COUNTER_OPERATOR')")
                .antMatchers("/").authenticated()
                .and()
                .formLogin()
                .usernameParameter("username").passwordParameter("password_hash")// It renders a login form
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and()
                .csrf().disable();
    }
}
