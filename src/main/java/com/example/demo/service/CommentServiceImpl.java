package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> getMovieComments(Long id) {
        return commentRepository.getMovieComments(id);
    }

    @Override
    public List<Comment> getShowComments(Long id) {
        return commentRepository.getShowComments(id);
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        Optional<Comment> commentDB = commentRepository.findById(comment.getId());
        if(commentDB.isPresent()) {
            if(comment.getName()==null){
                comment.setName(commentDB.get().getName());
            }
            if(comment.getDescription()==null){
                comment.setDescription(commentDB.get().getDescription());
            }
            if(comment.getIdReference()==null){
                comment.setIdReference(commentDB.get().getIdReference());
            }
            if(comment.getIsMovie()==null){
                comment.setMovie(commentDB.get().getIsMovie());
            }
            if(comment.getUserId()==null){
                comment.setUserId(commentDB.get().getUserId());
            }
            commentRepository.save(comment);
            return commentDB.get();
        }else{
            return null;
        }
    }

    @Override
    public void deleteMovieComments(Long id) {
        commentRepository.deleteMovieComments(id);
    }

    @Override
    public Comment deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) {
            commentRepository.delete(comment.get());
            return comment.get();
        }else{
            return null;
        }
    }
}