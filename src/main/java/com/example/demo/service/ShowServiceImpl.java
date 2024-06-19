package com.example.demo.service;

import com.example.demo.model.Show;
import com.example.demo.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl implements ShowService {

    private ShowRepository showRepository;

    @Autowired
    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public Show getShow(Long id) {
        return showRepository.findById(id).orElse(null);
    }

    @Override
    public List<Show> getShows() {
        return showRepository.findAll();
    }

    @Override
    public Show createShow(Show show) {
        return showRepository.save(show);
    }

    @Override
    public Show updateShow(Show show) {
        Optional<Show> showDB = showRepository.findById(show.getId());
        if(showDB.isPresent()) {
            if(show.getName()==null){
                show.setName(showDB.get().getName());
            }
            if(show.getDescription()==null){
                show.setDescription(showDB.get().getDescription());
            }
            if(show.getSeriesCount()==0){
                show.setSeriesCount(showDB.get().getSeriesCount());
            }
            if(show.getEpisodeCount()==0){
                show.setEpisodeCount(showDB.get().getEpisodeCount());
            }
            if(show.getLength()==0){
                show.setLength(showDB.get().getLength());
            }
            if(show.getUserId()==null){
                show.setUserId(showDB.get().getUserId());
            }
            return showRepository.save(show);
        }else{
            return null;
        }
    }

    @Override
    public Show deleteShow(Long id) {
        Optional<Show> show = showRepository.findById(id);
        if(show.isPresent()) {
            showRepository.delete(show.get());
            return show.get();
        }else{
            return null;
        }
    }
}
