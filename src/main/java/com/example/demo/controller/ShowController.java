package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Show;
import com.example.demo.service.CommentService;
import com.example.demo.service.MyUserDetails;
import com.example.demo.service.ShowService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@RestController
@Controller
@RequestMapping("/show")
public class ShowController {

    private ShowService showService;
    private CommentService commentService;

    @Autowired
    public ShowController(ShowService showService, CommentService commentService){
        this.showService = showService; this.commentService = commentService;
    }

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("shows", showService.getShows());
        return "main";
    }

    @GetMapping("/newShow")
    public String newShow(Model model){
        model.addAttribute("show", new Show());
        return "newShow";
    }

    @PostMapping("/addShow")
    public String addShow(@ModelAttribute Show show, Model model, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        show.setUserId(getCurrentUserId(authentication));
        showService.createShow(show);
        return"redirect:/movie/";
    }

    @GetMapping("/{id}")
    public String getShow(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userId", getCurrentUserId(authentication));
        model.addAttribute("show", showService.getShow(id));
        model.addAttribute("comments", commentService.getShowComments(id));
        model.addAttribute("newComment", new Comment());
        return "show";
    }

    @GetMapping("/edit/{id}")
    public String editShow(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(showService.getShow(id).getUserId()!=getCurrentUserId(authentication)){
            return "redirect:/movie/";
        }
        model.addAttribute("show", showService.getShow(id));
        model.addAttribute("newShow", new Show());
        return "editShow";
    }

    @PostMapping("/editShow")
    public String updateShow(@ModelAttribute Show show, Model model, RedirectAttributes redirectAttributes) {
        System.out.println(show.getId() + show.getName() + show.getDescription());
        showService.updateShow(show);
        return"redirect:/movie/";
    }
    @PostMapping("/delete/{id}")
    public String deleteShow(@PathVariable Long id) {
        commentService.deleteMovieComments(id);
        showService.deleteShow(id);
        return"redirect:/movie/";
    }
    private Long getCurrentUserId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }

}

