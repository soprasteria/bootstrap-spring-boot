package com.ssg.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by mvincent on 21/09/2016.
 */
@Entity
public class Registration {

  @EmbeddedId
  @Getter @Setter
  private RegistrationId id;
  @Getter @Setter
  @Column(columnDefinition = "boolean default false")
  private boolean picked;

  @Embeddable
  public static class RegistrationId implements Serializable {

    @Getter @Setter
    private long eventId;
    @Getter @Setter
    private long accountId;
  }
}
