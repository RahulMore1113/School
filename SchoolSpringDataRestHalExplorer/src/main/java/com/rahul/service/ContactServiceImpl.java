package com.rahul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

		return repo.save(contact).getContactId() > 0;

	}

	@Override
	public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir) {

		int pageSize = 5;

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
				sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

		return repo.findByStatusWithQuery(EazySchoolConstants.OPEN, pageable);
//        return repo.findOpenMsgs(EazySchoolConstants.OPEN, pageable);
//        return repo.findOpenMsgsNative(EazySchoolConstants.OPEN, pageable);           //Dynamic Sorting will not work for @NamedNativeQuery parameter

	}

	@Override
	public boolean updateMsgStatus(int id) {

//        return repo.findById(id).map(contact -> {
//
//            contact.setStatus(EazySchoolConstants.CLOSE);
//
//            return repo.save(contact).getUpdatedBy() != null;
//
//        }).orElse(false);

//        return repo.updateStatusById(EazySchoolConstants.CLOSE, id) > 0;
//        return repo.updateMsgStatus(EazySchoolConstants.CLOSE, id) > 0;
		return repo.updateMsgStatusNative(EazySchoolConstants.CLOSE, id) > 0;

	}

}
