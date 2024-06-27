package com.rahul.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.constants.EazySchoolConstants;
import com.rahul.model.Contact;
import com.rahul.repo.ContactRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactServiceImpl implements IContactService {

	@Autowired
	private ContactRepo repo;

	@Override
	public boolean saveMessageDetails(Contact contact) {

		contact.setStatus(EazySchoolConstants.OPEN);
		contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
		contact.setCreatedAt(LocalDateTime.now());

		return repo.save(contact).getContactId() > 0;

	}

	@Override
	public List<Contact> findMsgsWithOpenStatus() {

		return repo.findByStatus(EazySchoolConstants.OPEN);

	}

	@Override
	public boolean updateMsgStatus(int id, String updatedBy) {

		return repo.findById(id).map(contact -> {

			contact.setStatus(EazySchoolConstants.ANONYMOUS);
			contact.setUpdatedBy(updatedBy);
			contact.setUpdatedAt(LocalDateTime.now());

			return repo.save(contact).getUpdatedBy() != null;

		}).orElse(false);

	}

}
