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

import com.rahul.model.Courses;
import com.rahul.model.EazyClass;
import com.rahul.model.Person;
import com.rahul.repo.CoursesRepo;
import com.rahul.repo.EazyClassRepo;
import com.rahul.repo.PersonRepo;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EazyClassRepo classRepo;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private CoursesRepo coursesRepo;

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

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses() {

        return new ModelAndView("courses_secure.html")
//                .addObject("courses", coursesRepo.findByOrderByNameDesc())                                                        //Static Sorting
//                .addObject("courses", coursesRepo.findAll(Sort.by("name").descending()))                                          //Dynamic Sorting
//                .addObject("courses", coursesRepo.findAll(Sort.by("name").descending().and(Sort.by("fees").descending())))        //Dynamic Sorting
                .addObject("courses", coursesRepo.findAll())
                .addObject("course", new Courses());

    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute Courses course) {

        coursesRepo.save(course);

        return new ModelAndView("redirect:/admin/displayCourses");

    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(@RequestParam int id, HttpSession session, @RequestParam(required = false) String error) {

        return coursesRepo.findById(id)
                .map(course -> {
                    session.setAttribute("courses", course);
                    return new ModelAndView("course_students.html")
                            .addObject("courses", course)
                            .addObject("person", new Person())
                            .addObject("errorMessage", error != null ? "Invalid Email entered!!" : null);
                })
                .orElseGet(() -> new ModelAndView("error_page.html").addObject("errorMessage", "Course not found!"));

    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(@ModelAttribute Person person, HttpSession session) {

        Courses courses = Optional.ofNullable((Courses) session.getAttribute("courses"))
                .orElseThrow(() -> new IllegalStateException("No courses in session"));

        return Optional.ofNullable(personRepo.readByEmail(person.getEmail()))
                .filter(p -> p.getPersonId() > 0)
                .map(p -> {
                    p.getCourses().add(courses);
                    courses.getPersons().add(p);
                    personRepo.save(p);
                    session.setAttribute("courses", courses);
                    return new ModelAndView("redirect:/admin/viewStudents?id=" + courses.getCourseId());
                })
                .orElseGet(() -> new ModelAndView("redirect:/admin/viewStudents?id=" + courses.getCourseId() + "&error=true"));

    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam int personId, HttpSession session) {

        Courses courses = (Courses) session.getAttribute("courses");

        return personRepo.findById(personId)
                .map(person -> {
                    person.getCourses().remove(courses);
                    courses.getPersons().remove(person);
                    personRepo.save(person);
                    session.setAttribute("courses", courses);
                    return new ModelAndView("redirect:/admin/viewStudents?id=" + courses.getCourseId());
                })
                .orElseGet(() -> new ModelAndView("error_page.html").addObject("errorMessage", "Course not found!"));

    }

}
