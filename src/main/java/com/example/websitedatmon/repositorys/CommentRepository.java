package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.Comment;
import com.example.websitedatmon.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Comment save(Comment comment);
    List<Comment> findCommentByFood(Food food);
    Comment findCommentByOrderId(Integer orderId);
}
