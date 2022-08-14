package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.*;
import com.example.websitedatmon.serviceImpls.FeedbackServiceImpl;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class FeedbackController {
    @Autowired
    FeedbackServiceImpl feedbackService;

    Middleware middleware = new Middleware();

    @GetMapping({ "/feedback"})
    public ModelAndView index(String msg)
    {
        List<Feedback> list = feedbackService.findAll();
        ModelAndView mv = new ModelAndView("feedback");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @GetMapping({ "/send"})
    public ModelAndView send(String msg)
    {
        List<Feedback> list = feedbackService.findAll();
        ModelAndView mv = new ModelAndView("send");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @PostMapping(value = "/feedback-add")
    public ModelAndView add(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:send");
        User user = middleware.middlewareUser(request);
        String name = request.getParameter("title");
        String desciption = request.getParameter("description");
        Feedback feedBack = new Feedback();
        feedBack.setUser(user);
        feedBack.setTitle(name);
        feedBack.setDescription(desciption);
        feedBack.setCreated(java.time.LocalDate.now());
        feedBack.setStatus(1);
        feedbackService.save(feedBack);
        mv.addObject("msg","success");
        return mv;
    }
}
