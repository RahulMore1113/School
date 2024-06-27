package com.rahul.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rahul.model.Holiday;

@Repository
public interface HolidaysRepo extends CrudRepository<Holiday, String> {
}
