package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Comment;
import com.example.websitedatmon.entity.User;
import com.example.websitedatmon.services.CommentService;
import com.example.websitedatmon.services.FoodService;
import com.example.websitedatmon.services.UserService;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;

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
        int userId = user.getId();
        comment.setFoodId(foodId);
        comment.setUserId(userId);
        comment.setOrderId(orderId);
        comment.setCreatedAt(java.time.LocalDate.now().toString());
        comment.setIsActive(1);
        commentService.save(comment);
        mv.addObject("msg","success");
        return mv;
    }

}
