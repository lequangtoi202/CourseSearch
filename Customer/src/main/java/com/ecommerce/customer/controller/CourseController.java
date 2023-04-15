package com.ecommerce.customer.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/courses")
    public String courses(Model model, @RequestParam(value = "c", required = false)String query){
        List<Course> courses;
        if (query != null){
            courses = courseService.getByKeyword(query);
        }else{
            courses = courseService.findAll();
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("categories", categories);
        return "courses";
    }

    @GetMapping("/course-detail/{id}")
    public String courseDetail(@PathVariable("id")long id, Model model){
        Course course = courseService.getById(id);
        model.addAttribute("course", course);
        return "course-detail";
    }

    @GetMapping("/category/{id}")
    public String categoryProgram(@PathVariable("id")long id, Model model){
        List<Course> courses = courseService.getByCategoryId(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("categories", categories);
        return "courses";
    }

}
