package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.Request;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {

    Request findRequestById(int id);
    List<Request> findAll();
    List<Request> findAll(Sort sort);
    Request save(Request request);
    void deleteById(int id);
    List<Request> findAllByUserId(Integer userId);

    @Query(value = "SELECT * FROM `request` WHERE created = CURDATE()",nativeQuery = true)
    List<Request> findToday();

    @Query(value = "SELECT * FROM `request` WHERE created = CURDATE() AND user_id = ?",nativeQuery = true)
    List<Request> findTodayAndUser(int userId);
}
