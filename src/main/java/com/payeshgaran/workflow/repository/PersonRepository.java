package com.payeshgaran.workflow.repository;

import com.payeshgaran.workflow.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository  extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.priority = ?1")
    Person getByPriority(Long priority);

    @Query("select p from Person p where p.userId = ?1")
    Person getByUserId(String userId);

}
