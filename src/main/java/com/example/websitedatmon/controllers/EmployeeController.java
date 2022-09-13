package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Role;
import com.example.websitedatmon.entity.User;
import com.example.websitedatmon.serviceImpls.RoleServiceImpl;
import com.example.websitedatmon.serviceImpls.UserServiceImpl;
import com.example.websitedatmon.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class EmployeeController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;

    @GetMapping({ "/employee"})
    public ModelAndView index(String msg)
    {
        List<User> list = userService.listEmployee();
        ModelAndView mv = new ModelAndView("employee");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @GetMapping({ "/profile"})
    public ModelAndView getProfile(String msg)
    {
        List<User> list = userService.listEmployee();
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @GetMapping({ "/pageCheck"})
    public ModelAndView pageCheck(String msg)
    {
        ModelAndView mv = new ModelAndView("checkId");
        mv.addObject("msg",msg);
        return mv;
    }

//    @GetMapping({ "/result"})
//    public ModelAndView checkId(String msg,HttpServletRequest request)
//    {
//        ModelAndView mv = new ModelAndView("result");
////        int id = Integer.parseInt(request.getParameter("id"));
////        var emp = userService.findUserById(id);
////        mv.addObject("emp",emp);
//        mv.addObject("msg",msg);
//        return mv;
//    }

    @PostMapping(value = "/employee-add")
    public ModelAndView add(HttpServletRequest request, @RequestParam("file") MultipartFile image){
        ModelAndView mv = new ModelAndView("redirect:employee");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String username = request.getParameter("taikhoan");
        String password =request.getParameter("matkhau");
        User user = new User();
        user.setIsActive(1);
        user.setEmail(email);
        user.setFullName(fullname);
        user.setPhoneNumber(sdt);
        user.setUserName(username);
        user.setPassword(password);
        Role role = roleService.findRoleById(3);
        user.setRoleId(3);
        String fileName = "";
        try {
            fileName = FileUtil.upload(image,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setImage(fileName);
        userService.save(user);
        mv.addObject("msg","success");
        return mv;
    }

    @PostMapping(value = "/employee-change")
    public ModelAndView changAvatar(HttpServletRequest request, @RequestParam("file") MultipartFile image){
        ModelAndView mv = new ModelAndView("redirect:employee");
        HttpSession session = request.getSession();
        int id = Integer.parseInt(session.getId());
        var user = userService.findUserById(id);
        String avatar = "";
        try {
            avatar = FileUtil.upload(image,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
            user.setImage(avatar);
            userService.save(user);
        mv.addObject("msg","success");
        return mv;
    }
}
