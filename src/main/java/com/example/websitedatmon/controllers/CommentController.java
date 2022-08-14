package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Comment;
import com.example.websitedatmon.services.CommentService;
import com.example.websitedatmon.services.FoodService;
import com.example.websitedatmon.services.UserService;
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

    @PostMapping("/comment")
    public ModelAndView comment(HttpServletRequest request, Comment comment){
        ModelAndView mv = new ModelAndView("redirect:history");
        int foodId = Integer.parseInt(request.getParameter("food_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        comment.setFood(foodService.findFoodById(foodId));
        comment.setUser(userService.findUserById(userId));
        comment.setCreatedAt(java.time.LocalDate.now().toString());
        commentService.save(comment);
        mv.addObject("msg","success");
        return mv;
    }

}
