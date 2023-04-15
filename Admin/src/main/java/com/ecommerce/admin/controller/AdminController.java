package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.CourseDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Course;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseService courseService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new LocalDateEditor("yyyy-MM-dd"));
    }

    @GetMapping("/categories")
    public String categories(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return "redirect:/login";
        } else {
            List<Category> categories = categoryService.findAll();
            model.addAttribute("title", "Categories");
            model.addAttribute("categories", categories);
            model.addAttribute("size", categories.size());
        }
        return "categories";
    }

    @GetMapping("/add-category")
    public String addCategoryForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return "redirect:/login";
        } else {
            model.addAttribute("categoryDto", new CategoryDto());
        }
        return "add-category";
    }

    @PostMapping("/save-category")
    public String addCategory(RedirectAttributes attributes,
                              Principal principal,
                              @ModelAttribute("categoryDto") CategoryDto categoryDto,
                              @RequestParam("imageCate")MultipartFile cateImage){
        try{
            categoryService.save(cateImage, categoryDto);
            attributes.addFlashAttribute("success", "Added category successfully");
        }catch (Exception e){
            attributes.addFlashAttribute("failed", "Failed to add category");
        }
        return "redirect:/categories";
    }

    @PostMapping("/update-category/{id}")
    public String updateCategory(@PathVariable("id")long id,
                                 RedirectAttributes attributes,
                              Principal principal,
                              @ModelAttribute("categoryDto") CategoryDto categoryDto,
                              @RequestParam("imageCate")MultipartFile cateImage){
        if (principal == null)
            return "redirect:/login";
        try{
            categoryService.update(cateImage, categoryDto);
            attributes.addFlashAttribute("success", "Updated category successfully");
        }catch (Exception e){
            attributes.addFlashAttribute("failed", "Failed to update category");
        }
        return "redirect:/categories";
    }

    @GetMapping("/update-category/{id}")
    public String updateCategory(@PathVariable("id")long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return "redirect:/login";
        } else {
            model.addAttribute("title", "Update category");
            CategoryDto categoryDto = categoryService.getById(id);
            if (categoryDto == null)
                model.addAttribute("failed", "Category not found");
            model.addAttribute("categoryDto", categoryDto);
        }
        return "update-category";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id")long id,
                               RedirectAttributes attributes,
                               Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return "redirect:/login";
        } else {
            try {
                Category category = categoryService.getByCateId(id);
                if (category == null)
                    attributes.addFlashAttribute("failed", "Category not found!");
                else {
                    categoryService.delete(category);
                    attributes.addFlashAttribute("success", "Deleted category successfully");
                }
            } catch (Exception e) {
                attributes.addFlashAttribute("failed", "Failed to delete category");
            }
        }
        return "redirect:/categories";
    }
    /*==================COURSE=========================*/
    @GetMapping("/courses")
    public String courses(@RequestParam(value = "kw", required = false)String kw, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return "redirect:/login";
        } else {
            List<Course> courses;
            if (kw == null) {
                courses = courseService.findAll();
            } else {
                courses = courseService.getByName(kw);
            }
            model.addAttribute("title", "Courses");
            model.addAttribute("courses", courses);
            model.addAttribute("size", courses.size());
        }
        return "courses";
    }

    @GetMapping("/add-course")
    public String addCourseForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return "redirect:/login";
        } else {
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("courseDto", new CourseDto());
        }
        return "add-course";
    }

    @PostMapping("/save-course")
    public String addCourse(RedirectAttributes attributes,
                            Principal principal,
                            @ModelAttribute("courseDto") CourseDto courseDto,
                            @RequestParam("imageCourse")MultipartFile imageCourse){
        if (principal == null)
            return "redirect:/login";
        try{
            courseService.save(imageCourse, courseDto);
            attributes.addFlashAttribute("success", "Added course successfully");
        }catch (Exception e){
            attributes.addFlashAttribute("failed", "Failed to add course");
        }
        return "redirect:/courses";
    }

    @GetMapping("/update-course/{id}")
    public String updateCourseForm(@PathVariable("id")long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return "redirect:/login";
        } else {
            List<Category> categories = categoryService.findAll();
            CourseDto courseDto = courseService.getByCourseId(id);
            if (courseDto == null)
                model.addAttribute("failed", "Category not found");
            model.addAttribute("categories", categories);
            model.addAttribute("courseDto", courseDto);
        }
        return "update-course";
    }

    @PostMapping("/update-course/{id}")
    public String updateCourse(@PathVariable("id")long id,
                               RedirectAttributes attributes,
                               Principal principal,
                               @ModelAttribute("courseDto") CourseDto courseDto,
                               @RequestParam("imageCourse")MultipartFile imageCourse){
        if (principal == null)
            return "redirect:/login";
        try{
            courseService.update(imageCourse, courseDto);
            attributes.addFlashAttribute("success", "Updated course successfully");
        }catch (Exception e){
            attributes.addFlashAttribute("failed", "Failed to update course");
        }
        return "redirect:/courses";
    }

    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable("id")long id,
                               RedirectAttributes attributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "redirect:/login";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            return "redirect:/login";
        } else {
            try{
                Course course = courseService.getById(id);
                if (course == null)
                    attributes.addFlashAttribute("failed", "Course not found!");
                else{
                    courseService.delete(course);
                    attributes.addFlashAttribute("success", "Deleted course successfully");
                }
            }catch (Exception e){
                attributes.addFlashAttribute("failed", "Failed to delete course");
            }
        }
        return "redirect:/courses";
    }
}
