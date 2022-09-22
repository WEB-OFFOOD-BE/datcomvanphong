package com.example.websitedatmon.controllers;

import com.example.websitedatmon.constans.ActiveConstants;
import com.example.websitedatmon.constans.StatusConstants;
import com.example.websitedatmon.entity.Comment;
import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.entity.Menu;
import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.repositorys.OrderRepository;
import com.example.websitedatmon.serviceImpls.FoodServiceImpl;
import com.example.websitedatmon.serviceImpls.MenuServiceImpl;
import com.example.websitedatmon.services.CommentService;
import com.example.websitedatmon.utils.FileUtil;
import com.example.websitedatmon.utils.MailUtil;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class FoodController {
    @Autowired
    FoodServiceImpl foodService;

    @Autowired
    CommentService commentService;

    @Autowired
    MenuServiceImpl menuService;

    @GetMapping({ "/food"})
    public ModelAndView index(String msg)
    {
        List<Food> list = foodService.findAll();
        ModelAndView mv = new ModelAndView("food");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @PostMapping(value = "/food-add")
    public ModelAndView add(HttpServletRequest request, @RequestParam("file") MultipartFile image){
        ModelAndView mv = new ModelAndView("redirect:food");
        String name = request.getParameter("name");
        String desciption = request.getParameter("description");
        Food food = new Food();
        food.setName(name);
        food.setDescription(desciption);
        food.setCreated(java.time.LocalDate.now());
        String fileName = "";
        try {
            fileName = FileUtil.upload(image,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        food.setImage(fileName);
        food.setAvgRate(0f);
        food.setIsActive(ActiveConstants.TRUE.getValue());
        foodService.save(food);
        mv.addObject("msg","success");
        return mv;
    }
    @PostMapping(value = "/food-update")
    public ModelAndView update(HttpServletRequest request,@RequestParam("file") MultipartFile image){
        ModelAndView mv = new ModelAndView("redirect:food");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("id"));
        String fileName = "";
        if(image.isEmpty()) {
            Food Food = foodService.findFoodById(id);
            fileName = Food.getImage();
        } else {
            try {
                fileName = FileUtil.upload(image,request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Food food = foodService.findFoodById(id);
        food.setName(name);
        food.setDescription(description);
        food.setImage(fileName);
        foodService.save(food);
        mv.addObject("msg","success");
        return mv;
    }
    @PostMapping(value = "/food-delete")
    public ModelAndView delete(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:food");
        String id = request.getParameter("id");
        int idc = Integer.parseInt(id);
        Food food = foodService.findFoodById(idc);
        String dateToday = java.time.LocalDate.now().toString();
        List<Orders> orders =  orderRepository.listSendMail(food.getId(), dateToday);
        String html = "Món ăn của bạn đã bị huỷ, vui lòng vào đặt lại";
        if(orders.size() > 0){
            for (Orders order : orders) {
                try {
                    MailUtil.sendHtmlMail(this.javaMailSenderImpl, order.getUser().getEmail(), "Thông báo", html);
                    orderRepository.delete(order);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        food.setIsActive(ActiveConstants.FALSE.getValue());
        foodService.save(food);
        mv.addObject("msg", "success");
        return mv;
    }

    @Autowired
    public JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping(value = "/detail/{foodId}")
    public ModelAndView detail(@PathVariable int foodId){
        ModelAndView mv = new ModelAndView("detail");
        Food obj = foodService.findFoodById(foodId);
        List<Comment> listC = commentService.findCommentByFood(obj);
        float sum = 0;
        for(int i = 0;i < listC.size();i++){
            sum = sum + listC.get(i).getRate();
        }
        float ave = (float)(sum/ listC.size());
        mv.addObject("food",obj);
        mv.addObject("listC",listC);
        mv.addObject("ave",ave);
        return mv;
    }
}
