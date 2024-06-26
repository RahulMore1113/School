package com.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahul.model.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
}
