package com.example.demo.model;

import jakarta.persistence.*;
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idReference;
    private Boolean isMovie;
    private String name;
    private String description;
    private Long userId;

    public Comment() {
    }

    public Comment(String name, String description, Long idReference, Boolean isMovie, Long userId) {
        this.name = name;
        this.description = description;
        this.idReference = idReference;
        this.isMovie = isMovie;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdReference() {
        return idReference;
    }

    public void setIdReference(Long idReference) {
        this.idReference = idReference;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsMovie() {
        return isMovie;
    }

    public void setMovie(Boolean movie) {
        isMovie = movie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}