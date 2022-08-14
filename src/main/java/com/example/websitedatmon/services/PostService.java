package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Post findPostById(int id);
    List<Post> findAll();
    List<Post> getNewPost(Sort sort);
    Post save(Post post);
    void deleteById(int id);
}
