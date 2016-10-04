package com.ssg.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
  @Getter
  private String userName;
  @Getter
  private String fullName;
  @Getter
  private String token;
  @Getter
  private Long accountId;
  @Getter
  private Boolean admin;
  @Getter
  private Boolean validator;
}
