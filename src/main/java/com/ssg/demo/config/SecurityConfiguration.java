package com.ssg.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private BasicUserDetailsService userService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    TokenProcessingFilter filter = new TokenProcessingFilter();
    filter.setAuthService(userService);

    http
      .addFilter(filter)
      // Authentication mechanism
      .httpBasic().and()
      .authorizeRequests()
      .antMatchers("/session").permitAll()
      .antMatchers(HttpMethod.GET, "/api/**").authenticated()
      .antMatchers(HttpMethod.POST, "/api/**").authenticated()
      .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
      .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
      .and().csrf().disable();
  }
}
