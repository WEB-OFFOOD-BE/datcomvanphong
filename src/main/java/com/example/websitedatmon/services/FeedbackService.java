package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FeedbackService {
    Feedback findFeedBackById(int id);
    List<Feedback> findAll();

    Feedback save(Feedback FeedBack);
    void deleteById(int id);
}
