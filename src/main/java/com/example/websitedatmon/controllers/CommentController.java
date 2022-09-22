package com.example.websitedatmon.controllers;

import com.example.websitedatmon.constans.ActiveConstants;
import com.example.websitedatmon.entity.Comment;
import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.entity.User;
import com.example.websitedatmon.repositorys.CommentRepository;
import com.example.websitedatmon.services.CommentService;
import com.example.websitedatmon.services.FoodService;
import com.example.websitedatmon.services.OrderService;
import com.example.websitedatmon.services.UserService;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    Middleware middleware = new Middleware();

    @PostMapping("/comment")
    public ModelAndView comment(HttpServletRequest request, Comment comment){
        ModelAndView mv = new ModelAndView("redirect:history");
        User user = middleware.middlewareUser(request);
        int foodId = Integer.parseInt(request.getParameter("food_id"));
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        int rate = Integer.parseInt(request.getParameter("rate"));
        var food = foodService.findFoodById(foodId);
        var comments = commentService.findCommentByFood(food);
        float avg = 0f;

        if(comments.size() >0){
            for(var cmt : comments){
                avg += cmt.getRate();
            }
            float avgFood = avg / comments.size();
            food.setAvgRate((float) (Math.ceil(avgFood * 10) / 10));
        }else {
            food.setAvgRate((float) (Math.ceil(rate * 10) / 10));
        }
        var order = orderService.findOrderById(orderId);
        order.setRate(ActiveConstants.TRUE.getValue());
        orderService.save(order);
        comment.setFoodId(foodId);
        comment.setUserId(user.getId());
        comment.setOrderId(orderId);
        comment.setCreatedAt(java.time.LocalDate.now().toString());
        comment.setIsActive(1);
        commentService.save(comment);
        mv.addObject("msg","success");
        return mv;
    }

    @GetMapping(value = "/comment/{foodId}")
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
