package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.LateOrder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface LateOrderService {
    List<LateOrder> findAllByUserIdAndCreated(Integer userId, LocalDate date);

    List<LateOrder> findAll();

    List<LateOrder> findAllByUserIdAndIsActive(Integer userId, Integer isActive);

    <S extends LateOrder> S save(S entity);

    Optional<LateOrder> findById(Integer integer);

    void deleteById(Integer integer);

    List<LateOrder> findAllByStatusId(Integer statusId);
}
