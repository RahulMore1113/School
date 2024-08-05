package com.rahul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.model.Address;
import com.rahul.model.Person;
import com.rahul.model.Profile;
import com.rahul.repo.PersonRepo;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfileServiceImpl implements IProfileService {

	@Autowired
	private PersonRepo personRepo;

	@Override
	public Profile getProfile(HttpSession session) {

		Person person = (Person) session.getAttribute("loggedInPerson");

		if (person == null) {
			log.error("User not logged in");
			return null;
		}

		return mapPersonToProfile(person);

	}

	@Override
	public void updateProfile(Profile profile, HttpSession session) {

		Person person = (Person) session.getAttribute("loggedInPerson");

		if (person == null) {
			log.error("User not logged in");
			throw new IllegalStateException("User not logged in");
		}

		updatePersonFromProfile(person, profile);
		Person savedPerson = personRepo.save(person);
		session.setAttribute("loggedInPerson", savedPerson);

	}

	private Profile mapPersonToProfile(Person person) {

		Profile profile = new Profile();
		profile.setName(person.getName());
		profile.setMobileNumber(person.getMobileNumber());
		profile.setEmail(person.getEmail());

		if (person.getAddress() != null) {
			Address address = person.getAddress();
			profile.setAddress1(address.getAddress1());
			profile.setAddress2(address.getAddress2());
			profile.setCity(address.getCity());
			profile.setState(address.getState());
			profile.setZipCode(address.getZipCode());
		}

		return profile;

	}

	private void updatePersonFromProfile(Person person, Profile profile) {

		person.setName(profile.getName());
		person.setEmail(profile.getEmail());
		person.setMobileNumber(profile.getMobileNumber());

		if (person.getAddress() == null) {
			person.setAddress(new Address());
		}

		Address address = person.getAddress();
		address.setAddress1(profile.getAddress1());
		address.setAddress2(profile.getAddress2());
		address.setCity(profile.getCity());
		address.setState(profile.getState());
		address.setZipCode(profile.getZipCode());

	}

}
