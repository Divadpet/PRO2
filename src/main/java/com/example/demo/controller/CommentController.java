package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Movie;
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
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/newComment")
    public String newComment(Model model){
        model.addAttribute("comment", new Comment());
        return "newComment";
    }

    @PostMapping("/addMovieComment")
    public String addMovieComment(@ModelAttribute Comment comment, Model model, RedirectAttributes redirectAttributes){
        comment.setMovie(true);
        commentService.createComment(comment);
        return"redirect:/movie/";
    }

    @PostMapping("/addShowComment")
    public String addShowComment(@ModelAttribute Comment comment, Model model, RedirectAttributes redirectAttributes){
        comment.setMovie(false);
        commentService.createComment(comment);
        return"redirect:/movie/";
    }

    @GetMapping("/edit/{id}")
    public String getComment(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(commentService.getComment(id).getUserId()!=getCurrentUserId(authentication)){
            return "redirect:/movie/";
        }
        model.addAttribute("comment", commentService.getComment(id));
        model.addAttribute("newComment", new Comment());
        return "editComment";
    }

    @PostMapping("/editComment")
    public String updateComment(@ModelAttribute Comment comment, Model model, RedirectAttributes redirectAttributes) {
        System.out.println(comment.getId() + comment.getName() + comment.getDescription());
        commentService.updateComment(comment);
        return"redirect:/movie/";
    }

    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
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

