package com.rahul.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rahul.model.Contact;

@Repository
public interface ContactRepo extends CrudRepository<Contact, Integer> {

	List<Contact> findByStatus(String status);

}
