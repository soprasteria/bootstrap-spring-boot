package com.ssg.demo.api;

import com.ssg.demo.config.BasicUserDetailsService;
import com.ssg.demo.config.SecurityUtils;
import com.ssg.demo.entity.Account;
import com.ssg.demo.model.Credentials;
import com.ssg.demo.model.User;
import com.ssg.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/session")
public class AuthenticationResource {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  BasicUserDetailsService authService;
  @Autowired
  AccountRepository accountRepository;

  private boolean dev = false;

  @RequestMapping(method = RequestMethod.POST)
  public Object login(@RequestBody Credentials credentials, @RequestParam("action") String action) {

    Object result = null;
    switch (action) {
      case "init":
        result = initSession(credentials);
        break;
      case "check":
        result = checkSession(credentials);
        break;
      default:
        result = "KO";
        break;
    }
    return result;
  }

  private User initSession(Credentials credentials) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
    Authentication authentInfo = authenticationManager.authenticate(authentication);
    SecurityContextHolder.getContext().setAuthentication(authentInfo);

    User user = null;
    try {
      Account account = accountRepository.findByUsername(credentials.getUsername());
      user = new User(account.getUsername(), account.getFullName(),
        SecurityUtils.createToken(authService.loadUserByUsername(account.getUsername())), account.getId(), account.isAdmin(), account.isValidator());
    } catch (Exception exc) {
      throw exc;
    }
    return user;
  }

  private String checkSession(Credentials credentials) {
    if (SecurityUtils.validateToken(credentials.getToken(), authService.loadUserByUsername(credentials.getUsername()))) {
      return "OK";
    } else {
      return "KO";
    }
  }
}
