package com.tms.speeding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().cors().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/login/index.html", "/login/favicon.ico", "/login/src/**", "/src/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login/login/", "/login/registration/", "/login/process/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login/index.html").loginProcessingUrl("/login/process/")
                .and()
                .logout()
                .logoutUrl("/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

}





































