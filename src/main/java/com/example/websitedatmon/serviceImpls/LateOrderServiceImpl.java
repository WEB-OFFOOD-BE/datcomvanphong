package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.LateOrder;
import com.example.websitedatmon.repositorys.LateOrderRepository;
import com.example.websitedatmon.services.LateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LateOrderServiceImpl implements LateOrderService {
    @Autowired
    LateOrderRepository lateOrderRepository;

    @Override
    public List<LateOrder> findAllByUserIdAndCreated(Integer userId, LocalDate date) {
        return lateOrderRepository.findAllByUserIdAndCreated(userId, date);
    }

    @Override
    public List<LateOrder> findAll() {
        return lateOrderRepository.findAll();
    }

    @Override
    public List<LateOrder> findAllByUserIdAndIsActive(Integer userId, Integer isActive) {
        return lateOrderRepository.findAllByUserIdAndIsActive(userId, isActive);
    }

    @Override
    public <S extends LateOrder> S save(S entity) {
        return lateOrderRepository.save(entity);
    }

    @Override
    public Optional<LateOrder> findById(Integer integer) {
        return lateOrderRepository.findById(integer);
    }

    @Override
    public void deleteById(Integer integer) {
        lateOrderRepository.deleteById(integer);
    }

    @Override
    public List<LateOrder> findAllByStatusId(Integer statusId) {
        return lateOrderRepository.findAllByStatusId(statusId);
    }
}
