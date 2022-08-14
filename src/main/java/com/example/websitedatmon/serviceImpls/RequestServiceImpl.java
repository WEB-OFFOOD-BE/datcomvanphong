package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.Request;
import com.example.websitedatmon.repositorys.RequestRepository;
import com.example.websitedatmon.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Override
    public Request findRequestById(int id) {
        return requestRepository.findRequestById(id);
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> findAll(Sort sort) {
        return requestRepository.findAll(sort);
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void deleteById(int id) {
        requestRepository.deleteById(id);
    }
}
