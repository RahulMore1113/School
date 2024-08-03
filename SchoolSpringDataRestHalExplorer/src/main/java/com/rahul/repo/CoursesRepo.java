package com.rahul.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.rahul.model.Courses;

@Repository
//@RepositoryRestResource(exported = false)
@RepositoryRestResource(path = "courses")
public interface CoursesRepo extends JpaRepository<Courses, Integer> {

	// Both are using for Static Sorting
	List<Courses> findByOrderByNameDesc();

	List<Courses> findByOrderByName();

}
