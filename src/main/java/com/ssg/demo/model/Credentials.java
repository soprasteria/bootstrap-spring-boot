package com.ssg.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Credentials {

  @Getter
  private String username;
  @Getter
  private String password;
  @Getter
  private String token;
}
