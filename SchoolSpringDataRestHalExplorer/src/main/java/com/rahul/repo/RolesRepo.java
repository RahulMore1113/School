package com.rahul.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahul.model.Roles;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {

	Roles getByRoleName(String roleName);

}
