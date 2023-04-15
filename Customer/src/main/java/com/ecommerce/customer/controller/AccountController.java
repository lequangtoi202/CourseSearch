package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.User;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new LocalDateEditor("yyyy-MM-dd"));
    }

    @GetMapping("/account")
    public String accountHome(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        Customer customer = customerService.getByUser(user);
        if (customer == null)
            model.addAttribute("customer", new Customer());
        else
            model.addAttribute("customer", customer);
        return "account";
    }

    @RequestMapping(value = "/update-info", method = {RequestMethod.POST})
    public String updateCustomer(@ModelAttribute("user") UserDto userDto,
                                 Principal principal,
                                 RedirectAttributes attributes){
        if (principal == null){
            return "redirect:/login";
        }
        if (userDto.getPassword().equals(userDto.getRepeatPassword())) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userService.saveInfo(userDto);
            attributes.addFlashAttribute("update_acc_success", "update user account success");
        }else{
            attributes.addFlashAttribute("password", "Password is not same!");
        }

        return "redirect:/account";


    }
}
