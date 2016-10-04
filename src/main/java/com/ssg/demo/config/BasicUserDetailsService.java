package com.ssg.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.ssg.demo.entity.Account;
import com.ssg.demo.repository.AccountRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Created by mvincent on 21/09/2016.
 */
@Service
@Component
public class BasicUserDetailsService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws
    UsernameNotFoundException {
    // Mock here
    // Account account = accountRepository.findByUsername(username);
    // if (account == null) {
    //  throw new UsernameNotFoundException("Unknown user");
    //}
    Account account = new Account(1L, "jdoe", "John Doe", new Date(), 0L, true, false, "");
    return new User(account.getUsername(), "", getGrantedAuthorities(username, account.isAdmin()));
  }
  private Collection<? extends GrantedAuthority> getGrantedAuthorities(String username, boolean admin) {
    if (admin) {
      return Arrays.asList(() -> "ROLE_USER", () -> "ROLE_ADMIN");
    }
    return Arrays.asList(() -> "ROLE_USER");
  }
}
