package com.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahul.model.EazyClass;

@Repository
public interface EazyClassRepo extends JpaRepository<EazyClass, Integer> {
}
