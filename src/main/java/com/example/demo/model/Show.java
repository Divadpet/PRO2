package com.example.demo.model;

import jakarta.persistence.*;
@Entity
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int seriesCount;
    private int episodeCount;
    private int length;
    private Long userId;

    public Show() {
    }

    public Show(String name, String description, int seriesCount, int episodeCount, int length, Long userId) {
        this.name = name;
        this.description = description;
        this.seriesCount = seriesCount;
        this.episodeCount = episodeCount;
        this.length = length;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getSeriesCount() {
        return seriesCount;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public int getLength() {
        return length;
    }

    public void setSeriesCount(int seriesCount) {
        this.seriesCount = seriesCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}