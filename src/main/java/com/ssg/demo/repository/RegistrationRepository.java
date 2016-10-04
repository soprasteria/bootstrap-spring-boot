package com.ssg.demo.repository;

import com.ssg.demo.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mvincent on 21/09/2016.
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Registration.RegistrationId> {

  List<Registration> findAllByIdEventId(Long eventId);

  List<Registration> findAllByIdEventIdAndPicked(Long eventId, boolean picked);
}
