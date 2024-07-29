package com.rahul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rahul.repo.CoursesRepo;
import com.rahul.repo.EazyClassRepo;
import com.rahul.repo.PersonRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private EazyClassRepo eazyClassRepo;

	@Autowired
	private PersonRepo personRepo;

	@Autowired
	private CoursesRepo coursesRepo;

	@GetMapping("/displayCourses")
	public ModelAndView displayCourses(HttpSession session) {

		return new ModelAndView("courses_enrolled.html").addObject("person", session.getAttribute("loggedInPerson"));

	}

}
