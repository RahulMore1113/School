package com.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahul.model.Courses;

@Repository
public interface CoursesRepo extends JpaRepository<Courses, Integer> {
}
