package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.TimeOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TimeOutRepository extends JpaRepository<TimeOut,Integer> {
}
