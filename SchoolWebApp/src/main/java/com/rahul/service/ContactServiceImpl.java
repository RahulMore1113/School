package com.rahul.service;

import org.springframework.stereotype.Service;

import com.rahul.model.Contact;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactServiceImpl implements IContactService {

	@Override
	public boolean saveMessage(Contact contact) {

		boolean isSaved = true;
		log.info(contact.toString());

		return isSaved;

	}

}
