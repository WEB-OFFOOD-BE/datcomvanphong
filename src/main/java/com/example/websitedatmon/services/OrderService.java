package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Orders findOrderById(int id);
    List<Orders> findAll();
    List<Orders> findOrderByUser(User user);
    List<Orders> findAll(Sort sort);
    Orders save(Orders order);
    void deleteById(int id);
    int update(int foodid, String date);
    List<Orders> listSendMail(int foodid, String date);
    List<Orders> findOrderByStatus(int status);
    List<Object[]> listFoodOrder();
    List<Object[]> listRequest();
}
