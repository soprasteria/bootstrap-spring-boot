package com.ssg.demo.api;

import com.ssg.demo.entity.Account;
import com.ssg.demo.entity.Event;
import com.ssg.demo.entity.Registration;
import com.ssg.demo.model.User;
import com.ssg.demo.repository.AccountRepository;
import com.ssg.demo.repository.EventRepository;
import com.ssg.demo.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API for events.
 */
@RestController
@RequestMapping("/api")
@RepositoryRestController
@PreAuthorize("hasRole('ROLE_USER')")
public class EventController {

  // Repositories
  @Autowired
  AccountRepository accountRepository;
  @Autowired
  EventRepository eventRepo;
  @Autowired
  RegistrationRepository registrationRepository;

  /**
   * @return all events
   */
  @RequestMapping(value = "/events", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<Event> findEvents() {
    List<Event> events = eventRepo.findAll();
    return events;
  }

  /**
   * @param id Event ID
   * @return Event with given ID
   */
  @RequestMapping(value = "/event/{id}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
  public Event findEvent(@PathVariable("id") Long id) {
    Event event = eventRepo.findOne(id);
    return event;
  }

  /**
   * Subscribe to an demo.
   *
   * @param accountId Account to register
   * @param eventId   Event to update
   * @param action Action on demo
   * @return Update result
   */
  @RequestMapping(value = "/event/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public String update(@RequestBody(required = false) Long accountId, @PathVariable("id") Long eventId, @RequestParam("action") String action) {
      switch (action) {
        case "register":
          if (!StringUtils.isEmpty(accountId)) {
            Registration registration = initRegistration(accountId, eventId);
            registrationRepository.save(registration);
          } else {
            return "KO";
          }
        break;
        case "unregister":
          if (!StringUtils.isEmpty(accountId)) {
            Registration registration = initRegistration(accountId, eventId);
            registrationRepository.delete(registration);
          } else {
            return "KO";
          }
          break;
      default:
        break;
    }
    return "OK";
  }

  /**
   * Initialize a subscription
   *
   * @param accountId Account
   * @param eventId   Event
   * @return subscription initialized
   */
  private Registration initRegistration(Long accountId, Long eventId) {
    Registration registration = new Registration();
    Registration.RegistrationId regId = new Registration.RegistrationId();
    regId.setAccountId(accountId);
    regId.setEventId(eventId);
    registration.setId(regId);
    return registration;
  }

  /**
   * Build user information from registration (i.e. account ID)
   * @param registration Registration
   * @return User built
   */
  private User buildUser(Registration registration) {
    Account account = accountRepository.findOne(registration.getId().getAccountId());
    User result = new User(account.getUsername(), account.getFullName(), "", account.getId(), false, false);
    return result;
  }
}
