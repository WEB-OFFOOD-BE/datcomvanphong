package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    Feedback findFeedBackById(int id);
    List<Feedback> findAll();

    Feedback save(Feedback FeedBack);
    void deleteById(int id);
}
