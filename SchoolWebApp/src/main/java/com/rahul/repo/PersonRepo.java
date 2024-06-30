package com.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahul.model.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {
}
