package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.Comment;
import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.repositorys.CommentRepository;
import com.example.websitedatmon.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentByFood(Food food) {
        return commentRepository.findCommentByFood(food);
    }
}
