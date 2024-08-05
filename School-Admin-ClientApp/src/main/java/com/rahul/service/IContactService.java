package com.rahul.service;

import org.springframework.data.domain.Page;

import com.rahul.model.Contact;

public interface IContactService {

    boolean saveMessageDetails(Contact contact);

    Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir);

    boolean updateMsgStatus(int id);

}
