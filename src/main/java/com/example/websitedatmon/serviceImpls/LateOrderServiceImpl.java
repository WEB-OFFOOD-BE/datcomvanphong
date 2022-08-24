package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.LateOrder;
import com.example.websitedatmon.repositorys.LateOrderRepository;
import com.example.websitedatmon.services.LateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LateOrderServiceImpl implements LateOrderService {
    @Autowired
    LateOrderRepository lateOrderRepository;

    @Override
    public List<LateOrder> findAll() {
        return lateOrderRepository.findAll();
    }

    @Override
    public <S extends LateOrder> S save(S entity) {
        return lateOrderRepository.save(entity);
    }

    @Override
    public Optional<LateOrder> findById(Integer integer) {
        return lateOrderRepository.findById(integer);
    }
}
