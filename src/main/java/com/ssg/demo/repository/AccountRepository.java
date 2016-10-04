package com.ssg.demo.repository;

import com.ssg.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cchariere on 07/09/2016.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByUsername(String username);
}
