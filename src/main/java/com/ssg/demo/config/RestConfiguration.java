package com.ssg.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import com.ssg.demo.entity.Account;
import com.ssg.demo.entity.Event;

/**
 * Created by cchariere on 12/09/2016.
 */


@Configuration
public class RestConfiguration  extends RepositoryRestConfigurerAdapter {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Event.class, Account.class);

  }
}

