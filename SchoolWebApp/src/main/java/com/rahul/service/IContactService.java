package com.rahul.service;

import java.util.List;

import com.rahul.model.Contact;

public interface IContactService {

	boolean saveMessageDetails(Contact contact);

	List<Contact> findMsgsWithOpenStatus();

	boolean updateMsgStatus(int id);

}
