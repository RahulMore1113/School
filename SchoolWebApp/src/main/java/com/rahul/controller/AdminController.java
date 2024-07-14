package com.rahul.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rahul.model.EazyClass;
import com.rahul.model.Person;
import com.rahul.repo.EazyClassRepo;
import com.rahul.repo.PersonRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EazyClassRepo classRepo;

    @Autowired
    private PersonRepo personRepo;

    @GetMapping("/displayClasses")
    public ModelAndView displayClasses() {

        return new ModelAndView("classes.html")
                .addObject("eazyClasses", classRepo.findAll())
                .addObject("eazyClass", new EazyClass());

    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(@ModelAttribute EazyClass eazyClass) {

        classRepo.save(eazyClass);

        return new ModelAndView("redirect:/admin/displayClasses");

    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(@RequestParam int id) {

        classRepo.findById(id).ifPresent(eazyClass -> {
            eazyClass.getPersons().forEach(person -> {
                person.setEazyClass(null);
                personRepo.save(person);
            });
            classRepo.deleteById(id);
        });

        return new ModelAndView("redirect:/admin/displayClasses");

    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(@RequestParam int classId, HttpSession session, @RequestParam(required = false) String error) {

        return classRepo.findById(classId)
                .map(eazyClass -> {
                    session.setAttribute("eazyClass", eazyClass);
                    return new ModelAndView("students.html")
                            .addObject("eazyClass", eazyClass)
                            .addObject("person", new Person())
                            .addObject("errorMessage", error != null ? "Invalid Email Entered" : null);
                })
                .orElseGet(() -> new ModelAndView("redirect:/errorPage"));

    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(@ModelAttribute Person person, HttpSession session) {

        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");

        if (eazyClass == null) {
            return new ModelAndView("redirect:/errorPage");
        }

        return Optional.ofNullable(personRepo.readByEmail(person.getEmail()))
                .map(personEntity -> {
                    personEntity.setEazyClass(eazyClass);
                    personRepo.save(personEntity);

                    synchronized (eazyClass) {
                        eazyClass.getPersons().add(personEntity);
                        classRepo.save(eazyClass);
                    }

                    return new ModelAndView("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId());

                })
                .orElseGet(() -> new ModelAndView("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId() + "&error=true"));

    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(@RequestParam int personId, HttpSession session) {

        EazyClass eazyClass = Optional.ofNullable((EazyClass) session.getAttribute("eazyClass"))
                .orElseThrow(() -> new IllegalStateException("EazyClass not found in session"));

        Person person = personRepo.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));

        person.setEazyClass(null);
        eazyClass.getPersons().remove(person);
        classRepo.save(eazyClass);
        session.setAttribute("eazyClass", eazyClass);

        return new ModelAndView("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId());

    }

}
