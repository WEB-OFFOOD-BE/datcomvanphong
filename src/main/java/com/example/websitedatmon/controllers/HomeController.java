package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.Request;
import com.example.websitedatmon.entity.User;
import com.example.websitedatmon.repositorys.OrderRepository;
import com.example.websitedatmon.serviceImpls.OrderServiceImpl;
import com.example.websitedatmon.serviceImpls.RequestServiceImpl;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("user")
public class HomeController {

    Middleware middleware = new Middleware();

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    RequestServiceImpl requestService;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping({ "/home"})
    public ModelAndView home(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("index");
        User user = middleware.middlewareUser(request);
        if(user == null){
            mv = new ModelAndView("redirect:/user/login");
        }
        int sum = 0;
        int sumrequest = 0;
        /*List<Order> list = orderService.findOrderByStatus(2);*/
        List<Orders> list = orderRepository.getToday();
        sum = list.size();

        List<Request> listRequest = requestService.findAll();
        List<Orders> done = orderRepository.getTodayAndStatus(1);
        List<Orders> progress = orderRepository.getTodayAndStatus(0);
        List<Orders> received = orderRepository.getTodayAndStatus(2);

        //loinhuan
        var loinhuan = sum - sumrequest;
        //list food order
        List<Object[]> listfoodorder = orderService.listFoodOrder();
        List<Object[]> listrequest1 = orderService.listRequest();
        mv.addObject("listfoodorder",listfoodorder);
        mv.addObject("listrequest1",listrequest1);
        mv.addObject("sum",sum);
        mv.addObject("done",done.size());
        mv.addObject("progress",progress.size());
        mv.addObject("received",received.size());
        return mv;
    }
}
