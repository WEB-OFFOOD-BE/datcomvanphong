package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.LateOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LateOrderRepository extends JpaRepository<LateOrder,Integer> {

    List<LateOrder> findAllByStatusId(Integer statusId);
}
