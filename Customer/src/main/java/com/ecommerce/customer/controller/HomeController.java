package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.*;
import com.ecommerce.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new LocalDateEditor("yyyy-MM-dd"));
    }

    @GetMapping("/")
    public String home(Model model, Principal principal, HttpSession session){
        if (principal != null)
            session.setAttribute("username", principal.getName());
        List<Course> courses = courseService.findAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("categories", categories);
        return "index";
    }



    @PostMapping("/update-customer")
    public String updateCustomer(@ModelAttribute("customer")Customer customer, Principal principal, RedirectAttributes attributes){
        if (principal == null)
            return "redirect:/login";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Customer customer1 = customerService.getByUser(user);
        if (customer1 != null){
            customer1.setEmail(customer.getEmail());
            customer1.setAddress(customer.getAddress());
            customer1.setPhone(customer.getPhone());
            customer1.setFullName(customer.getFullName());
            customer1.setGender(customer.isGender());
            customer1.setDateOfBirth(customer.getDateOfBirth());
            try{
                customerService.update(customer1);
                attributes.addFlashAttribute("update_success", "Update successfully!");

            }catch (Exception e){
                attributes.addFlashAttribute("update_failed", "Update failed!");
            }
        }else{
            CustomerDto customerSave = new CustomerDto();
            customerSave.setEmail(customer.getEmail());
            customerSave.setAddress(customer.getAddress());
            customerSave.setPhone(customer.getPhone());
            customerSave.setFullName(customer.getFullName());
            customerSave.setGender(customer.isGender());
            customerSave.setDateOfBirth(customer.getDateOfBirth());
            try{
                customerService.save(customerSave, user);
                attributes.addFlashAttribute("success", "Update successfully!");

            }catch (Exception e){
                attributes.addFlashAttribute("failed", "Update failed!");
            }
        }
        return "redirect:/account";
    }

    @GetMapping("/categories")
    public String categories(Model model, @RequestParam(value = "q", required = false)String query){
        List<Category> categories;
        if (query != null){
           categories = categoryService.getByName(query);
        }else{
            categories = categoryService.findAll();
        }
        model.addAttribute("categories", categories);
        return "categories";
    }
}
