package com.example.websitedatmon.controllers;

import com.example.websitedatmon.constans.CommonConstants;
import com.example.websitedatmon.constans.TimeOutConstants;
import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.entity.Menu;
import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.User;
import com.example.websitedatmon.repositorys.OrderRepository;
import com.example.websitedatmon.repositorys.TimeOutRepository;
import com.example.websitedatmon.serviceImpls.FoodServiceImpl;
import com.example.websitedatmon.serviceImpls.MenuServiceImpl;
import com.example.websitedatmon.serviceImpls.OrderServiceImpl;
import com.example.websitedatmon.serviceImpls.UserServiceImpl;
import com.example.websitedatmon.utils.MailUtil;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class MenuController {
    @Autowired
    MenuServiceImpl menuService;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    FoodServiceImpl foodService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TimeOutRepository timeOutRepository;

    @Autowired
    public JavaMailSenderImpl javaMailSenderImpl;

    Middleware middleware = new Middleware();

    @GetMapping({ "/menu"})
    public ModelAndView index(String msg)
    {
        List<Menu> list = menuService.getTomorrow();
        List<Food> listF = foodService.findAll();
        List<Food> listIdFood = list.stream().map(Menu::getFood).collect(Collectors.toList());
        List<Integer> listInt = listIdFood.stream().map(Food::getId).collect(Collectors.toList());
        listF = listF.stream().filter(x -> !listInt.contains(x.getId())).collect(Collectors.toList());
        LocalDateTime now = LocalDateTime.now();
        ModelAndView mv = new ModelAndView("menu");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        mv.addObject("listF",listF);
        mv.addObject("checkSize",listF.size());
        mv.addObject("hour",now.getHour());
        if(now.getHour() >= CommonConstants.START_ADD && now.getHour() <= CommonConstants.END_ADD){
            mv.addObject("check",true);
        }else{
            mv.addObject("check",false);
        }

        var timeOut = timeOutRepository.findById(TimeOutConstants.ORDER.getValue()).orElse(null);
        if(now.getHour() >= timeOut.getStartTime() && now.getHour() <= timeOut.getEndTime()){
            mv.addObject("checkOrder",true);
        }else{
            mv.addObject("checkOrder",false);
        }
//        mv.addObject("checkOrder",true);
        return mv;
    }

    @PostMapping(value = "/menu-add")
    public ModelAndView add(HttpServletRequest request , @RequestParam(value = "items[]") List<String> arrId){
        ModelAndView mv = new ModelAndView("redirect:menu");
        Date today = new Date();
        String quantity = "0";
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        String dateString  = LocalDate.ofInstant(tomorrow.toInstant(), ZoneId.systemDefault()).toString();

        for (int i = 0; i < arrId.size(); i++) {
            List<Menu> check = menuService.findMenued(Integer.parseInt(arrId.get(i)),dateString);
            if(check.size() == 0){
                Food food = foodService.findFoodById(Integer.parseInt(arrId.get(i)));
                Menu menu = new Menu();
                menu.setFoodId(food.getId());
                menu.setIsActive(1);
//                menu.setFood(food);
                menu.setDate(dateString);
                menu.setStatus(1);
                menuService.save(menu);
            }
        }
        mv.addObject("msg","success");
        return mv;
    }

    @PostMapping(value = "/menu-delete")
    public ModelAndView delete(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:menu");
        String id = request.getParameter("id");
        int idc = Integer.parseInt(id);
        menuService.deleteById(idc);
        mv.addObject("msg", "success");
        return mv;
    }

    @PostMapping(value = "/menu-order")
    public ModelAndView order(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:menu");
        User user = middleware.middlewareUser(request);
        var orderCheck = orderRepository.getToday(user.getId());
        if (orderCheck.size() == 0){
            int id = Integer.parseInt(request.getParameter("id"));
            Orders order = new Orders();
            order.setFoodId(id);
            order.setUserId(user.getId());
            order.setQuantity(1);
            order.setCreated(java.time.LocalDate.now().toString());
            order.setStatus(0);
            orderService.save(order);
            mv.addObject("msg","success");
        }
        mv.addObject("msg","failed");
        return mv;
    }

    @PostMapping(value = "/send-mail")
    public ModelAndView sendMail(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:menu");
        String html = "<p>Đã có thực đơn món ăn cho ngày mai !</p>";
        List<User> list = userService.listEmployee();
        for (User user : list) {
            try {
                MailUtil.sendHtmlMail(this.javaMailSenderImpl,user.getEmail(),"Thông báo",html);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        mv.addObject("msg","success");
        return mv;
    }
}
