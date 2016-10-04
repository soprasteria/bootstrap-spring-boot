package com.ssg.demo.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by mvincent on 21/09/2016.
 */
public class TokenProcessingFilter extends UsernamePasswordAuthenticationFilter {

  @lombok.Setter
  private BasicUserDetailsService authService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
    ServletException {
    HttpServletRequest httpRequest = this.getAsHttpRequest(request);

    String authToken = this.extractAuthTokenFromRequest(httpRequest);
    String userName = SecurityUtils.getUserNameFromToken(authToken);

    if (userName != null) {
      UserDetails userDetails = this.authService.loadUserByUsername(userName);
      if (SecurityUtils.validateToken(authToken, userDetails)) {

        UsernamePasswordAuthenticationToken authentication;
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(request, response);
  }


  private HttpServletRequest getAsHttpRequest(ServletRequest request) {
    if (!(request instanceof HttpServletRequest)) {
      throw new RuntimeException("Expecting an HTTP request");
    }
    return (HttpServletRequest) request;
  }


  private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {

        /* Get token from header */
    String authToken = httpRequest.getHeader("X-Auth-Token");

		/* If token not found get it from request parameter */
    if (authToken == null) {
      authToken = httpRequest.getParameter("token");
    }
    return authToken;
  }
}
