package com.example.websitedatmon.services;

import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {
    Menu findMenuById(int id);
    List<Menu> findMenuByFood(Food food);
    List<Menu> findMenued(int foodid, String date);
    List<Menu> findAll();
    Menu save(Menu menu);
    void deleteById(int id);
    List<Menu> getToday();
    List<Menu> getTomorrow();
}
