package com.rahul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rahul.constants.EazySchoolConstants;
import com.rahul.model.Person;
import com.rahul.model.Roles;
import com.rahul.repo.PersonRepo;
import com.rahul.repo.RolesRepo;

@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private PersonRepo personRepo;

	@Autowired
	private RolesRepo rolesRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public boolean createNewPerson(Person person) {

		Roles role = rolesRepo.getByRoleName(EazySchoolConstants.STUDENT_ROLE);

		if (role == null)
			throw new RuntimeException("Role not found : " + EazySchoolConstants.STUDENT_ROLE);

		person.setRole(role);
		person.setPwd(encoder.encode(person.getPwd()));

		return personRepo.save(person).getPersonId() > 0;

	}

}
