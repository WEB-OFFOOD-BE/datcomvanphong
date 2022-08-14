package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.Post;
import com.example.websitedatmon.repositorys.PostRepository;
import com.example.websitedatmon.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Post findPostById(int id) {
        return postRepository.findPostById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getNewPost(Sort sort) {
        return postRepository.findAll(sort);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deleteById(int id) {
        postRepository.deleteById(id);
    }
}
