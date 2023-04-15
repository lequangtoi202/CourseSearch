package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.User;
import com.ecommerce.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthenticationController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(name = "logout", required = false) String logout,
                        HttpServletRequest request,
                        Principal principal,
                        RedirectAttributes attributes){
        if (logout != null){

            SecurityContextHolder.clearContext();


            HttpSession session = request.getSession(false);
            if(session != null) {
                session.invalidate();
            }
            attributes.addFlashAttribute("logout", "You have been logged out");
            return "redirect:/login";
        }
        return "login";
    }


    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("customerDto", new UserDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("customerDto") UserDto userDto,
                                  BindingResult result,
                                  Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", userDto);
                return "register";
            }
            User customer = userService.findByUsername(userDto.getUsername());
            if(customer != null){
                model.addAttribute("username", "Username have been registered");
                model.addAttribute("customerDto", userDto);
                return "register";
            }

            if (userDto.getPassword().equals(userDto.getRepeatPassword())){
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userService.save(userDto);
                model.addAttribute("success", "Register successfully");
            }else{
                model.addAttribute("password", "Password is not same");
                model.addAttribute("customerDto", userDto);
                return "register";
            }

        }catch (Exception e){
            model.addAttribute("error", "Server have ran some problems");
            model.addAttribute("customerDto", userDto);
        }
        return "register";
    }
}
