package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.Comment;
import com.example.websitedatmon.entity.Food;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    Comment save(Comment comment);
    List<Comment> findCommentByFood(Food food);
}
