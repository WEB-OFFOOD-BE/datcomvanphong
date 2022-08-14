package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.Request;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestService {
    Request findRequestById(int id);
    List<Request> findAll();
    List<Request> findAll(Sort sort);
    Request save(Request request);
    void deleteById(int id);
}
