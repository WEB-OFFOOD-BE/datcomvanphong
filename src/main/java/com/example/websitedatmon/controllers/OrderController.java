package com.example.websitedatmon.controllers;

import com.example.websitedatmon.constans.TimeOutConstants;
import com.example.websitedatmon.entity.*;
import com.example.websitedatmon.repositorys.OrderRepository;
import com.example.websitedatmon.repositorys.TimeOutRepository;
import com.example.websitedatmon.serviceImpls.FoodServiceImpl;
import com.example.websitedatmon.serviceImpls.MenuServiceImpl;
import com.example.websitedatmon.serviceImpls.OrderServiceImpl;
import com.example.websitedatmon.services.LateOrderService;
import com.example.websitedatmon.services.RequestService;
import com.example.websitedatmon.services.UserService;
import com.example.websitedatmon.utils.MailUtil;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class OrderController {
    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    MenuServiceImpl menuService;

    @Autowired
    RequestService requestService;

    @Autowired
    public JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    FoodServiceImpl foodService;

    @Autowired
    TimeOutRepository timeOutRepository;

    @Autowired
    LateOrderService lateOrderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    Middleware middleware = new Middleware();

    @GetMapping({"/order"})
    public ModelAndView index(String msg) {
        ModelAndView mv = new ModelAndView("order");
        Sort sort = Sort.by("id").descending();
        List<Orders> list = orderRepository.getToday();
        List<Menu> menus = menuService.getToday();
        mv.addObject("msg", msg);
        mv.addObject("list", list);
        mv.addObject("menus", menus);
        return mv;
    }

    @GetMapping({"/history"})
    public ModelAndView history(String msg) {
        ModelAndView mv = new ModelAndView("history");
        Sort sort = Sort.by("id").descending();
        List<Orders> list = orderService.findAll(sort);
        mv.addObject("msg", msg);
        mv.addObject("list", list);
        return mv;
    }

//    @GetMapping({"/current-order"})
//    public ModelAndView currentOrder(String msg) {
//        ModelAndView mv = new ModelAndView("current");
//        Sort sort = Sort.by("id").descending();
//        List<Orders> list = orderService.findAll(sort);
//        Orders first = list.get(0);
//        var food = foodService.findFoodById(first.getFoodId());
//        mv.addObject("msg", msg);
//        mv.addObject("first", food);
//        return mv;
//    }

    @GetMapping({"/late-order"})
    public ModelAndView lateOrder(String msg) {
        ModelAndView mv = new ModelAndView("lateOrders");
        Sort sort = Sort.by("id").descending();
        List<LateOrder> list = lateOrderService.findAllByStatusId(1);
//        var food = foodService.findFoodById(first.getFoodId());
        mv.addObject("msg", msg);
        mv.addObject("myRequests", list);
        return mv;
    }

    @PostMapping(value = "/order-edit")
    public ModelAndView update(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:order");
        int status = Integer.parseInt(request.getParameter("status"));
        int id = Integer.parseInt(request.getParameter("id"));
        Orders order = orderService.findOrderById(id);
        order.setStatus(status);
        orderService.save(order);
        mv.addObject("msg", "success");
        return mv;
    }

    @PostMapping(value = "/order-group")
    public ModelAndView group(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:order");
        String html = "<p>Món ăn đã hoàn thành. Vui lòng tới nhà bếp để nhận món!</p>";
        int id = Integer.parseInt(request.getParameter("id"));
        int menuId = Integer.parseInt(request.getParameter("menuId"));
        String datetoday = java.time.LocalDate.now().toString();
        var menu = menuService.findMenuById(menuId);
        menu.setIsDone(2);
        menuService.save(menu);
        orderService.update(id, datetoday);
        List<Orders> list = orderService.listSendMail(id, datetoday);
        for (Orders order : list) {
            try {
                MailUtil.sendHtmlMail(this.javaMailSenderImpl, order.getUser().getEmail(), "Thông báo", html);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        mv.addObject("msg", "success");
        return mv;
    }

    @GetMapping({"/current"})
    public ModelAndView current(String msg) {
        ModelAndView mv = new ModelAndView("requestProgressing");
        var myRequests = requestService.findRequest(3);
        mv.addObject("msg", msg);
        mv.addObject("myRequests", myRequests);
        return mv;
    }

    @GetMapping({"/time-out"})
    public ModelAndView timeOut(String msg) {
        var timeOut = timeOutRepository.findById(TimeOutConstants.ORDER.getValue()).orElse(null);
        ModelAndView mv = new ModelAndView("timeOut");
        mv.addObject("msg", msg);
        mv.addObject("timeOut", timeOut);
        return mv;
    }

    @GetMapping({"/result/{userId}"})
    public ModelAndView getToday(String msg, String msgUser, @PathVariable int userId) {
        User obj = userService.findUserById(userId);
        ModelAndView mv = new ModelAndView();
        if (obj == null) {
            msg = "failed";
            mv.addObject("msg", msg);
            mv = new ModelAndView("checkId");
            return mv;
        } else {
            msgUser = "user";
            var orderCheck = orderRepository.getTodayById(obj.getId());
            if (orderCheck.size() == 0) {
                msg = "notyet";
            } else {
                var order1 = orderCheck.stream().findFirst().orElse(null);
                var food = foodService.findFoodById(order1.getFoodId());
//                EmployeeInfo order = EmployeeInfo.builder()
//                        .user(obj)
//                        .food(food)
//                        .build();
                mv.addObject("order", order1);
                msg = "ok";
            }
        }
        mv = new ModelAndView("result");
        mv.addObject("msg", msg);
        mv.addObject("msgUser", msgUser);
        return mv;
    }

    @PostMapping(value = "/set-time-out")
    public ModelAndView add(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:time-out");
        var timeOut = timeOutRepository.findById(TimeOutConstants.ORDER.getValue()).orElse(null);
        if (timeOut != null) {
            int startTime = Integer.parseInt(request.getParameter("startTime"));
            int endTime = Integer.parseInt(request.getParameter("endTime"));
            timeOut.setStartTime(startTime);
            timeOut.setEndTime(endTime);
            timeOutRepository.save(timeOut);
        }
        mv.addObject("msg", "success");
        return mv;
    }

}
