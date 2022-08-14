package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.repositorys.FoodRepository;
import com.example.websitedatmon.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Override
    public Food findFoodById(int id) {
        return foodRepository.findFoodById(id);
    }

    @Override
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Food save(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public void deleteById(int id) {
        foodRepository.deleteById(id);
    }
}
