package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.User;
import com.example.websitedatmon.repositorys.OrderRepository;
import com.example.websitedatmon.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Orders findOrderById(int id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> findOrderByStatus(int status) {
        return orderRepository.findOrderByStatus(status);
    }

    @Override
    public List<Orders> findOrderByUser(User user) {
        return orderRepository.findOrderByUser(user);
    }

    @Override
    public List<Orders> findAll(Sort sort) {
        return orderRepository.findAll(sort);
    }

    @Override
    public Orders save(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Object[]> listFoodOrder() {
        return orderRepository.listFoodOrder();
    }

    @Override
    public List<Object[]> listRequest() {
        return orderRepository.listRequest();
    }

    @Override
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public int update(int foodid) {
        return  orderRepository.update(foodid);
    }
    @Override
    public List<Orders> listSendMail(int foodid, String date) {
        return  orderRepository.listSendMail(foodid,date);
    }
}
