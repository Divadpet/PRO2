package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.Comment;
import org.springframework.security.core.Authentication;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.MovieService;
import com.example.demo.service.MyUserDetails;
import com.example.demo.service.ShowService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@RestController
@Controller
@RequestMapping("/movie")
public class MovieController {

    private MovieService movieService;
    private ShowService showService;
    private CommentService commentService;

    @Autowired
    public MovieController(MovieService movieService, ShowService showService, CommentService commentService){
        this.movieService = movieService;
        this.showService = showService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("movies", movieService.getMovies());
        model.addAttribute("shows", showService.getShows());
        return "main";
    }

    @GetMapping("/newMovie")
    public String newMovie(Model model){
        model.addAttribute("movie", new Movie());
        return "newMovie";
    }

    @PostMapping("/addMovie")
    public String addMovie(@ModelAttribute Movie movie, Model model, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        movie.setUserId(getCurrentUserId(authentication));
        movieService.createMovie(movie);
        System.out.println(movie.getUserId());
        return"redirect:/movie/";
    }

    @GetMapping("/{id}")
    public String getMovie(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userId", getCurrentUserId(authentication));
        model.addAttribute("movie", movieService.getMovie(id));
        model.addAttribute("comments", commentService.getMovieComments(id));
        model.addAttribute("newComment", new Comment());
        System.out.println(getCurrentUserId(authentication));
        return "movie";
    }

    @GetMapping("/edit/{id}")
    public String editMovie(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(movieService.getMovie(id).getUserId()!=getCurrentUserId(authentication)){
            return "redirect:/movie/";
        }
        model.addAttribute("movie", movieService.getMovie(id));
        model.addAttribute("newMovie", new Movie());
        return "editMovie";
    }

    @PostMapping("/editMovie")
    public String updateMovie(@ModelAttribute Movie movie, Model model, RedirectAttributes redirectAttributes) {
        movieService.updateMovie(movie);
        return"redirect:/movie/";
    }
    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        commentService.deleteMovieComments(id);
        movieService.deleteMovie(id);
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

