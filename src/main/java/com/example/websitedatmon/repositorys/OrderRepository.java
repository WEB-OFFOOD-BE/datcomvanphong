package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {

    Orders findOrderById(int id);
    List<Orders> findOrderByStatus(int status);
    List<Orders> findAll();
    List<Orders> findOrderByUser(User user);
    List<Orders> findAll(Sort sort);
    Orders save(Orders order);
    void deleteById(int id);

    List<Orders> findOrderByUserIdAndCreated(Integer userId, String date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `orders` SET status = 1 WHERE food_id = ? AND created = DATE_SUB(CURDATE(), INTERVAL 1 DAY)",nativeQuery = true)
    int update(int foodid);

    @Query(value = "SELECT * FROM `orders` WHERE food_id = ? AND created = DATE_SUB(CURDATE(), INTERVAL 1 DAY)",nativeQuery = true)
    List<Orders> getFoodInToday(int foodid);

    @Query(value = "SELECT  * FROM `orders` WHERE food_id = ? AND created = ? AND is_active = 1",nativeQuery = true)
    List<Orders> listSendMail(int foodid, String date);

    @Query(value = "SELECT a.food_id as 'foodid', b.name as 'name', b.image as 'image', COUNT(a.id) as 'soluongbanduoc' FROM `orders` as a, `food` as b WHERE a.food_id = b.id  GROUP BY a.food_id",nativeQuery = true)
    List<Object[]> listFoodOrder();

    @Query(value = "SELECT a.food_id as 'foodid', b.name as 'name', b.image as 'image', COUNT(a.food_id) as 'soluonghoantra' FROM `orders` as a, `food` as b, `request` as c WHERE a.food_id = b.id AND c.order_id = a.id GROUP BY a.food_id",nativeQuery = true)
    List<Object[]> listRequest();

    @Query(value = "SELECT * FROM `orders` WHERE created = CURDATE() AND is_active = 1 AND user_id = ?",nativeQuery = true)
    List<Orders> getTodayById(int userid);

    @Query(value = "SELECT * FROM `orders` WHERE created = CURDATE() AND is_active = 1 AND user_id = ? AND status = 0",nativeQuery = true)
    List<Orders> getTodayByIdAndStatus(int userid);

    @Query(value = "SELECT * FROM `orders` WHERE created = DATE_SUB(CURDATE(), INTERVAL 1 DAY) AND is_active = 1",nativeQuery = true)
    List<Orders> getYesterday();

    @Query(value = "SELECT * FROM `orders` WHERE created = CURDATE() AND is_active = 1",nativeQuery = true)
    List<Orders> getToday();

    @Query(value = "SELECT * FROM `orders` WHERE created = CURDATE() and status = ? AND is_active = 1",nativeQuery = true)
    List<Orders> getTodayAndStatus(int status);

    @Query(value = "SELECT * FROM `orders` WHERE created > CURDATE() and status = ? AND is_active = 1",nativeQuery = true)
        List<Orders> getTomorrowAndStatus(int status);
}
