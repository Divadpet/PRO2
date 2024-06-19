package com.example.demo.repository;

import com.example.demo.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT e FROM Comment e WHERE e.idReference = :id AND e.isMovie = true")
    List<Comment> getMovieComments(@Param("id") Long id);

    @Query("SELECT e FROM Comment e WHERE e.idReference = :id AND e.isMovie = false")
    List<Comment> getShowComments(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment e WHERE e.idReference = :id")
    void deleteMovieComments(@Param("id") Long id);

}