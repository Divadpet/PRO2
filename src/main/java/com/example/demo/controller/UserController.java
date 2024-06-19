package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.User;
import com.example.demo.service.MyUserDetails;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@RestController
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){
        userService.saveUser(user);
        return "redirect:/user/login";
    }
/*
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){
        userService.saveUser(user);
        return"login";
    }
*/
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model){
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null && authentication.isAuthenticated()){
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            Long userId = myUserDetails.getId();
            HttpSession session= request.getSession();
            session.setAttribute("userId", userId);
        }*/
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model){
        return "logout";
    }

    @PostMapping("/loginFinish")
    public String loginFinish(@ModelAttribute Movie movie, Model model, RedirectAttributes redirectAttributes){
        return "redirect:/movie/";
    }

/*
    @PostMapping("/LoginFinish")
    public String addLogin(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){
        return"login";
    }
*/
}
