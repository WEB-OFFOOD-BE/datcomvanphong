package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.Feedback;
import com.example.websitedatmon.repositorys.FeedbackRepository;
import com.example.websitedatmon.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public Feedback findFeedBackById(int id) {
        return feedbackRepository.findFeedBackById(id);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback save(Feedback feedBack) {
        return feedbackRepository.save(feedBack);
    }

    @Override
    public void deleteById(int id) {
        feedbackRepository.deleteById(id);
    }
}
