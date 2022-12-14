package com.example.websitedatmon.controllers;

import com.example.websitedatmon.constans.ActiveConstants;
import com.example.websitedatmon.constans.CommonConstants;
import com.example.websitedatmon.constans.TimeOutConstants;
import com.example.websitedatmon.entity.*;
import com.example.websitedatmon.model.HistoryResponse;
import com.example.websitedatmon.repositorys.MenuRepository;
import com.example.websitedatmon.repositorys.OrderRepository;
import com.example.websitedatmon.repositorys.RequestRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
    RequestRepository requestRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    UserService userService;

    Middleware middleware = new Middleware();

    @GetMapping({"/order"})
    public ModelAndView index(String msg) {
        ModelAndView mv = new ModelAndView("order");
        Sort sort = Sort.by("id").descending();
        List<Orders> list = orderRepository.getToday();
        List<Orders> listYesterday = orderRepository.getYesterday();
        List<Menu> menus = menuService.getToday();
        List<Menu> menusYesterday = menuRepository.getYesterday();

        List<Menu> menu = menus;
        if (menu.size() == 0){
            menu = menusYesterday;
        }
        if (list.size() == 0){
            list = listYesterday;
        }
        mv.addObject("msg", msg);
        mv.addObject("list", list);
        mv.addObject("menus", menu);
        return mv;
    }

    @GetMapping({"/history"})
    public ModelAndView history(HttpServletRequest request, String msg) {
        ModelAndView mv = new ModelAndView("history");
        Date today = new Date();
//        String dateString  = LocalDate.ofInstant(today.toInstant(), ZoneId.systemDefault()).toString();
        String dateString  = java.time.LocalDate.now().toString();

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(today);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        List<Orders> list = orderService.findAll();
        list.sort((d1, d2) -> {
            return d2.getId() - d1.getId();
        });
        Sort sort = Sort.by("id").descending();
        var user = Middleware.middlewareUser(request);
        List<HistoryResponse> responses = new ArrayList<>();
        for (var od : list) {
            var historyResponse = HistoryResponse.builder()
                    .order(od)
                    .isToday(Objects.equals(od.getCreated(), dateString))
                    .build();
            responses.add(historyResponse);
        }
        for (var od : list) {
            HistoryResponse build = HistoryResponse.builder()
                    .isToday(Objects.equals(od.getCreated(), dateString))
                    .order(od).build();
            responses.add(build);
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() >= CommonConstants.START_ADD && now.getHour() <= CommonConstants.END_ADD) {
            mv.addObject("check", true);
        } else {
            mv.addObject("check", false);
        }

        var timeOut = timeOutRepository.findById(TimeOutConstants.ORDER.getValue()).orElse(null);
        if (now.getHour() >= timeOut.getStartTime() && now.getHour() <= timeOut.getEndTime()) {
            mv.addObject("checkOrder", true);
        } else {
            mv.addObject("checkOrder", false);
        }

        mv.addObject("msg", msg);
        mv.addObject("list", responses);
        mv.addObject("currentHours", hours);
        return mv;
    }


    @GetMapping({"/late-order"})
    public ModelAndView lateOrder(String msg, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("lateOrders");
        User user = middleware.middlewareUser(request);
        List<LateOrder> list = lateOrderService.findAllByUserIdAndIsActive(user.getId(), ActiveConstants.TRUE.getValue());
        list.sort((d1, d2) -> {
            return d2.getId() - d1.getId();
        });
        mv.addObject("msg", msg);
        mv.addObject("myRequests", list);
        return mv;
    }

    @GetMapping({"/late-order-list"})
    public ModelAndView lateOrderList(String msg, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("pageLateOrders");
        List<LateOrder> list = lateOrderService.findAll();
        list.sort((d1, d2) -> {
            return d2.getId() - d1.getId();
        });
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
        String html = "<p>M??n ??n ???? ho??n th??nh. Vui l??ng t???i nh?? b???p ????? nh???n m??n!</p>";
        int id = Integer.parseInt(request.getParameter("id"));
        int menuId = Integer.parseInt(request.getParameter("menuId"));
        String datetoday = java.time.LocalDate.now().toString();
        var menu = menuService.findMenuById(menuId);
        menu.setIsDone(2);
        menuService.save(menu);
        var listFood = orderRepository.getFoodInToday(id);
        for(var food : listFood){
            food.setStatus(1);
            orderRepository.save(food);
        }
        orderService.findOrderById(id);
        List<Orders> list = orderService.listSendMail(id, datetoday);
        for (Orders order : list) {
            try {
                MailUtil.sendHtmlMail(this.javaMailSenderImpl, order.getUser().getEmail(), "Th??ng b??o", html);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        mv.addObject("msg", "success");
        return mv;
    }

    @GetMapping({"/old-request"})
    public ModelAndView oldRequest(String msg,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("requestProgressing");
        User user = middleware.middlewareUser(request);
        var myRequests = requestRepository.findAllByUserId(user.getId());
        myRequests.sort((d1, d2) -> {
            return d2.getId() - d1.getId();
        });
        mv.addObject("msg", msg);
        mv.addObject("myRequests", myRequests);
        return mv;
    }

    @PostMapping({"/request-delete"})
    public ModelAndView requestDelete(String msg,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:old-request");
        int id = Integer.parseInt(request.getParameter("id"));
        requestRepository.deleteById(id);
        mv.addObject("msg", "success");
        return mv;
    }

    @GetMapping({"/current"})
    public ModelAndView current(String msg,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("requestProgressing");
        User user = middleware.middlewareUser(request);
        var myRequests = requestRepository.findTodayAndUser(3);
        myRequests.sort((d1, d2) -> {
            return d2.getId() - d1.getId();
        });
        var rq = myRequests.get(0);

        if (rq != null){
            mv.addObject("myRequests", rq);
        }
        else {
            msg = "failed";
            mv.addObject("msg", msg);
        }
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

    @PostMapping(value = "/order-delete")
    public ModelAndView delete(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:history");
        String id = request.getParameter("id");
        int idc = Integer.parseInt(id);
        orderService.deleteById(idc);
        mv.addObject("msg", "success");
        return mv;
    }

}
