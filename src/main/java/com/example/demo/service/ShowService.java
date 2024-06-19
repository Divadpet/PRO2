package com.example.demo.service;

import com.example.demo.model.Show;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShowService {
    Show getShow(Long id);
    List<Show> getShows();
    Show createShow(Show show);
    Show updateShow(Show show);
    Show deleteShow(Long id);
}