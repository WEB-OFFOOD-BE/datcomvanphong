package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.constans.StatusConstants;
import com.example.websitedatmon.entity.Request;
import com.example.websitedatmon.model.RequestResponse;
import com.example.websitedatmon.repositorys.OrderRepository;
import com.example.websitedatmon.repositorys.RequestRepository;
import com.example.websitedatmon.repositorys.UserRepository;
import com.example.websitedatmon.services.BaseService;
import com.example.websitedatmon.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    BaseService baseService = new BaseService();

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

    @Override
    public List<RequestResponse> findRequest(int userId) {
        var ownerRequest = userRepository.findUserById(userId);
        var orders = orderRepository.findOrderByUser(ownerRequest);
        var currentOrder = orders.get(orders.size() - 1);
        var requests = requestRepository.findAllByUserId(3);
        List<RequestResponse> responses = new ArrayList<>();
        int i = 0;
        for (var item : requests){
             responses.add(
                     RequestResponse.builder()
                             .id(i)
                             .FoodName(item.getOrders().getFood().getName())
                             .image(item.getImage())
                             .food(item.getOrders().getFood())
                             .reason(item.getDescription())
                             .status(item.getStatus())
                             .order(item.getOrders())
                             .build()
             );
            i++;
        }
        return responses;
    }


}
