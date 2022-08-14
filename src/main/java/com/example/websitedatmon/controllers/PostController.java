package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Post;
import com.example.websitedatmon.serviceImpls.PostServiceImpl;
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
public class PostController {
    @Autowired
    PostServiceImpl postService;

    @GetMapping({ "/post"})
    public ModelAndView index(String msg)
    {
        List<Post> list = postService.findAll();
        ModelAndView mv = new ModelAndView("post");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @GetMapping({ "/newpost"})
    public ModelAndView newpost(String msg)
    {
        Sort sort = Sort.by("id").descending();
        List<Post> list = postService.getNewPost(sort);
        ModelAndView mv = new ModelAndView("newpost");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @PostMapping(value = "/post-add")
    public ModelAndView add(HttpServletRequest request, @RequestParam("file") MultipartFile image){
        ModelAndView mv = new ModelAndView("redirect:post");
        String name = request.getParameter("name");
        String desciption = request.getParameter("description");
        Post post = new Post();
        post.setTitle(name);
        post.setDescription(desciption);
        post.setCreated(java.time.LocalDate.now());
        String fileName = "";
        try {
            fileName = FileUtil.upload(image,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.setImage(fileName);
        postService.save(post);
        mv.addObject("msg","success");
        return mv;
    }
    @PostMapping(value = "/post-update")
    public ModelAndView update(HttpServletRequest request,@RequestParam("file") MultipartFile image){
        ModelAndView mv = new ModelAndView("redirect:post");
        String name = request.getParameter("name");
        String desciption = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("id"));
        String fileName = "";
        if(image.isEmpty()) {
            Post Post = postService.findPostById(id);
            fileName = Post.getImage();
        } else {
            try {
                fileName = FileUtil.upload(image,request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Post postud = postService.findPostById(id);
        postud.setTitle(name);
        postud.setDescription(desciption);
        postud.setImage(fileName);
        postService.save(postud);
        mv.addObject("msg","success");
        return mv;
    }
    @PostMapping(value = "/post-delete")
    public ModelAndView delete(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:post");
        String id = request.getParameter("id");
        int idc = Integer.parseInt(id);
        postService.deleteById(idc);
        mv.addObject("msg", "success");
        return mv;
    }
}
