package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    Post findPostById(int id);
    List<Post> findAll();
    List<Post> findAll(Sort sort);
    Post save(Post post);
    void deleteById(int id);
}
