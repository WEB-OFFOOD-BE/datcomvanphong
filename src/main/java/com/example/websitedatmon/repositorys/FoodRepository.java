package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.Food;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {
    Food findFoodById(int id);

    List<Food> findAll();
    List<Food> findAll(Sort sort);
    Food save(Food food);
    void deleteById(int id);
}
