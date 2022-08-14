package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.Request;
import com.example.websitedatmon.serviceImpls.OrderServiceImpl;
import com.example.websitedatmon.serviceImpls.RequestServiceImpl;
import com.example.websitedatmon.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class RequestController {
    @Autowired
    RequestServiceImpl requestService;

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping({ "/request"})
    public ModelAndView index(String msg)
    {
        Sort sort = Sort.by("id").descending();
        List<Request> list = requestService.findAll(sort);
        ModelAndView mv = new ModelAndView("request");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @PostMapping(value = "/request-add")
    public ModelAndView add(HttpServletRequest request, @RequestParam("file") MultipartFile image){
        ModelAndView mv = new ModelAndView("redirect:history");
        String desciption = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("id"));
        Orders order = orderService.findOrderById(id);
        Request request1 = new Request();
        request1.setDescription(desciption);
        request1.setCreated(java.time.LocalDate.now());
        request1.setOrders(order);
        request1.setStatus(0);
        String fileName = "";
        try {
            fileName = FileUtil.upload(image,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        request1.setImage(fileName);
        requestService.save(request1);
        mv.addObject("msg","success");
        return mv;
    }

    @PostMapping(value = "/request-update")
    public ModelAndView update(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:request");
        int status = Integer.parseInt(request.getParameter("status"));
        int id = Integer.parseInt(request.getParameter("id"));
        Request request1 = requestService.findRequestById(id);
        request1.setStatus(status);
        requestService.save(request1);
        mv.addObject("msg","success");
        return mv;
    }
}
