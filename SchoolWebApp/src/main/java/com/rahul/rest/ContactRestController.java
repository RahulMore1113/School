package com.rahul.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.constants.EazySchoolConstants;
import com.rahul.model.Contact;
import com.rahul.model.Response;
import com.rahul.repo.ContactRepo;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
public class ContactRestController {

    @Autowired
    private ContactRepo repo;

    @GetMapping("/getMessagesByStatus")
    public List<Contact> getMessagesByStatus(@RequestParam String status) {
        return repo.findByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact) {
        return contact.getStatus() != null ? repo.findByStatus(contact.getStatus()) : List.of();
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader String invocationFrom, @Valid @RequestBody Contact contact) {

        log.info("Header invocationFrom = {}", invocationFrom);
        repo.save(contact);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(new Response("200", "Message saved successfully"));

    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity) {

        requestEntity.getHeaders().forEach((key, value) ->
                log.info("Header '{}': {}", key, String.join("|", value))
        );

        Contact contact = requestEntity.getBody();
        if (contact == null || contact.getContactId() == null)
            return ResponseEntity
                    .badRequest()
                    .body(new Response("400", "Invalid request data"));

        try {
            repo.deleteById(contact.getContactId());
            return ResponseEntity
                    .ok(new Response("200", "Message deleted successfully"));
        } catch (Exception e) {
            log.error("Error deleting message", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("500", "Error deleting message"));
        }

    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq) {

        return repo.findById(contactReq.getContactId())
                .map(contact -> {
                    contact.setStatus(EazySchoolConstants.CLOSE);
                    repo.save(contact);
                    return ResponseEntity.ok(new Response("200", "Message closed successfully"));
                })
                .orElse(ResponseEntity.badRequest().body(new Response("400", "Invalid Contact ID received")));

    }


}
