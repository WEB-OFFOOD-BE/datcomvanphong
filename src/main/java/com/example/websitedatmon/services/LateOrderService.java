package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.LateOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LateOrderService {
    List<LateOrder> findAll();

    List<LateOrder> findAllByStatusId(Integer statusId);

    <S extends LateOrder> S save(S entity);

    Optional<LateOrder> findById(Integer integer);
}
