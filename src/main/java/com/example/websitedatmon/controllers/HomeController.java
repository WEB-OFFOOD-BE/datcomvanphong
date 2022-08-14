package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.Request;
import com.example.websitedatmon.entity.User;
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
        List<Orders> list = orderService.findAll();
        for (Orders order : list) {
//            sum = sum + Integer.parseInt(order.getFood().getPrice());
        }
        List<Request> listRequest = requestService.findAll();
        for (Request request1 : listRequest) {
//            sumrequest = sumrequest + Integer.parseInt(request1.getOrders().getFood().getPrice());
        }
        //loinhuan
        var loinhuan = sum - sumrequest;
        //list food order
        List<Object[]> listfoodorder = orderService.listFoodOrder();
        List<Object[]> listrequest1 = orderService.listRequest();
        mv.addObject("listfoodorder",listfoodorder);
        mv.addObject("listrequest1",listrequest1);
        mv.addObject("sum",sum);
        mv.addObject("sumrequest",sumrequest);
        mv.addObject("loinhuan",loinhuan);
        return mv;
    }
}
