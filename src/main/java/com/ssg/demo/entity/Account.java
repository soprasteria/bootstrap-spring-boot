package com.ssg.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cchariere on 07/09/2016.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

  @Id
  @Getter @Setter
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Getter @Setter
  private String username;
  @Getter @Setter
  private String fullName;
  @Getter @Setter
  private Date subscriptionDate;
  @Getter @Setter
  private Long malus;
  @Getter @Setter
  @Column(columnDefinition = "boolean default false")
  private boolean admin = false;
  @Getter @Setter
  @Column(columnDefinition = "boolean default false")
  private boolean validator = false;
  @Getter @Setter
  private String mail;
}

