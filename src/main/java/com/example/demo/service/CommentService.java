package com.example.demo.service;

import com.example.demo.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    Comment getComment(Long id);
    List<Comment> getMovieComments(Long id);
    List<Comment> getShowComments(Long id);
    void deleteMovieComments(Long id);
    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    Comment deleteComment(Long id);
}