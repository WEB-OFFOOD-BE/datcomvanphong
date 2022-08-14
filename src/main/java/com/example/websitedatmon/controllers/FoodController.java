package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Comment;
import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.entity.Menu;
import com.example.websitedatmon.serviceImpls.FoodServiceImpl;
import com.example.websitedatmon.serviceImpls.MenuServiceImpl;
import com.example.websitedatmon.services.CommentService;
import com.example.websitedatmon.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
        String price = request.getParameter("price");
        String desciption = request.getParameter("description");
        Food post = new Food();
        post.setName(name);
        post.setDescription(desciption);
        post.setCreated(java.time.LocalDate.now());
        String fileName = "";
        try {
            fileName = FileUtil.upload(image,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.setImage(fileName);
        foodService.save(post);
        mv.addObject("msg","success");
        return mv;
    }
    @PostMapping(value = "/food-update")
    public ModelAndView update(HttpServletRequest request,@RequestParam("file") MultipartFile image){
        ModelAndView mv = new ModelAndView("redirect:food");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String desciption = request.getParameter("description");
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
        Food postud = foodService.findFoodById(id);
        postud.setName(name);
        postud.setDescription(desciption);
        postud.setImage(fileName);
        foodService.save(postud);
        mv.addObject("msg","success");
        return mv;
    }
    @PostMapping(value = "/food-delete")
    public ModelAndView delete(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:food");
        String id = request.getParameter("id");
        int idc = Integer.parseInt(id);
        Food food = foodService.findFoodById(idc);
        List<Menu> check =  menuService.findMenuByFood(food);
        if(check.size() > 0){
            mv.addObject("msg", "fail");
        }else{
            foodService.deleteById(idc);
            mv.addObject("msg", "success");
        }
        return mv;
    }

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
