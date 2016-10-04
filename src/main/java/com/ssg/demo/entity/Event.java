package com.ssg.demo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssg.demo.utils.JsonDateSerializer;
import com.ssg.demo.utils.JsonDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by cchariere on 07/09/2016.
 *
 */
@Entity
public class Event implements Serializable {

  @Id
  @Getter @Setter
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  @Getter @Setter
  private String title;
  @Getter @Setter
  @JsonSerialize(using=JsonDateSerializer.class)
  private Date   date;
  @Getter @Setter
  private String description;
  @Getter @Setter
  @JsonSerialize(using=JsonDateTimeSerializer.class)
  private Date   participationEndingDate;
  @Getter @Setter
  private Integer maxWinners;
  @Getter @Setter
  @Column(columnDefinition = "boolean default false")
  private boolean toBeValidated;
  @Getter @Setter
  @Column(columnDefinition = "boolean default false")
  private boolean validated;

  @Getter @Setter
  @OneToMany(mappedBy = "id.eventId")
  private List<Registration> registrations;
}
