package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    Menu findMenuById(int id);

    List<Menu> findMenuByFood(Food food);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM `menu` WHERE food_id = ? AND date = ?",nativeQuery = true)
    List<Menu> findMenued(int foodid, String date);

    List<Menu> findAll();

    @Query(value = "SELECT * FROM `menu` WHERE date = DATE_ADD(CURDATE(),INTERVAL 1 DAY)",nativeQuery = true)
    List<Menu> getTomorrow();

    @Query(value = "SELECT * FROM `menu` WHERE date = CURDATE() AND is_done != 2",nativeQuery = true)
    List<Menu> getToday();

    Menu save(Menu menu);
    void deleteById(int id);
}
