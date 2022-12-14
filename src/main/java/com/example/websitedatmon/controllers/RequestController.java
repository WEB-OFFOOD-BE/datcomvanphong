package com.example.websitedatmon.controllers;

import com.example.websitedatmon.constans.ActiveConstants;
import com.example.websitedatmon.constans.CommonConstants;
import com.example.websitedatmon.constans.StatusConstants;
import com.example.websitedatmon.entity.LateOrder;
import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.Request;
import com.example.websitedatmon.entity.User;
import com.example.websitedatmon.repositorys.RequestRepository;
import com.example.websitedatmon.serviceImpls.OrderServiceImpl;
import com.example.websitedatmon.serviceImpls.RequestServiceImpl;
import com.example.websitedatmon.services.LateOrderService;
import com.example.websitedatmon.services.UserService;
import com.example.websitedatmon.utils.FileUtil;
import com.example.websitedatmon.utils.MailUtil;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class RequestController {
    @Autowired
    RequestServiceImpl requestService;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    LateOrderService lateOrderService;

    @Autowired
    UserService userService;

    @Autowired
    public JavaMailSenderImpl javaMailSenderImpl;

    Middleware middleware = new Middleware();

    @GetMapping({"/request"})
    public ModelAndView index(String msg) {
        List<Request> list = requestRepository.findToday();
        list.sort((d1, d2) -> {
            return d2.getId() - d1.getId();
        });
        ModelAndView mv = new ModelAndView("request");
        mv.addObject("msg", msg);
        mv.addObject("list", list);
        return mv;
    }

    @PostMapping(value = "/request-add")
    public ModelAndView add(HttpServletRequest request, @RequestParam("file") MultipartFile image) {
        ModelAndView mv = new ModelAndView("redirect:history");
        String desciption = request.getParameter("description");
        var user = Middleware.middlewareUser(request);
        int id = Integer.parseInt(request.getParameter("id"));
        Request request1 = new Request();
        request1.setDescription(desciption);
        request1.setCreated(java.time.LocalDate.now());
        request1.setOrderId(id);
        request1.setStatus(1);
        request1.setIsActive(ActiveConstants.TRUE.getValue());
        request1.setUserId(user.getId());
        String fileName = "";

        if (image != null) {
            try {
                fileName = FileUtil.upload(image, request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            request1.setImage(fileName);
        }
        requestService.save(request1);
        mv.addObject("msg", "success");
        return mv;
    }


    @PostMapping(value = "/late-order")
    public ModelAndView lateOrder(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:menu");
        String description = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("id"));
        User user = middleware.middlewareUser(request);
        assert user != null;
        var order = lateOrderService.findAllByUserIdAndCreated(user.getId(), java.time.LocalDate.now());
        if (order.size() == 0) {
            LateOrder lateOrder = new LateOrder();
            lateOrder.setFoodId(id);
            lateOrder.setCreated(java.time.LocalDate.now());
            lateOrder.setStatusId(StatusConstants.PROGRESSING.getValue());
            lateOrder.setReason(description);
            lateOrder.setUserId(user.getId());
            lateOrder.setIsActive(ActiveConstants.TRUE.getValue());
            lateOrderService.save(lateOrder);
            String html = "???? c?? ????n ?????t c??m mu???n m???i b???n ki???u tra danh s??ch ?????t c??m mu???n";
            try {
                MailUtil.sendHtmlMail(this.javaMailSenderImpl, CommonConstants.MY_EMAIL, "Th??ng b??o", html);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            mv.addObject("msg", "success");
        } else {
            mv.addObject("msg", "failed");
        }
        return mv;
    }

    @PostMapping(value = "/late-order-edit")
    public ModelAndView updateLateOrders(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:late-order-list");
        int status = Integer.parseInt(request.getParameter("status"));
        int id = Integer.parseInt(request.getParameter("id"));
        LateOrder lateOrder = lateOrderService.findById(id).orElse(null);
        if (status == 100) {
            var requests = lateOrderService.findAllByStatusId(StatusConstants.PROGRESSING.getValue());
            for (var item : requests) {
                lateOrderService.deleteById(item.getId());
            }
        } else {
            if (lateOrder != null) {
                if (status == StatusConstants.DELETED.getValue()) {
                    lateOrderService.deleteById(lateOrder.getId());
                }
            } else {
                lateOrder.setStatusId(status);
                lateOrderService.save(lateOrder);
                if (status == 4){
                    Orders order = new Orders();
                    order.setFoodId(id);
                    order.setUserId(lateOrder.getUserId());
                    order.setRate(ActiveConstants.FALSE.getValue());
                    order.setCreated(java.time.LocalDate.now().toString());
                    order.setStatus(0);
                    order.setIsActive(ActiveConstants.TRUE.getValue());
                    orderService.save(order);
                }
                String html = "";
                switch (status) {
                    case 4:
                        html = "<p>Y??u c???u ?????t mu???n c???a b???n ???? ???????c x??c nh???n. B???n vui l??ng ch??? email ti???p theo</p>";
                    case 5:
                        html = "<p>Y??u c???u ?????t mu???n c???a b???n ???? b??? t??? ch???i</p>";
                    case 2:
                        html = "<p>M??n ??n c???a b???n ???? s???n s??ng, m???i b???n xu???ng b???p ????? nh???n c??m</p>";
                }

                try {
                    MailUtil.sendHtmlMail(this.javaMailSenderImpl, CommonConstants.MY_EMAIL, "Th??ng b??o", html);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }

        mv.addObject("msg", "success");
        return mv;
    }

    @PostMapping(value = "/request-update")
    public ModelAndView update(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:request");
        int status = Integer.parseInt(request.getParameter("status"));
        int id = Integer.parseInt(request.getParameter("id"));
        String html = null;
        Request request1 = requestService.findRequestById(id);
        var ownerId = request1.getUserId();
        var ownerRequest = userService.findUserById(ownerId);
        switch (status) {
            case 4:
                html = "<p>Y??u c???u c???a b???n ???? ???????c x??c nh???n. B???n vui l??ng ch??? email ti???p theo</p>";
            case 5:
                html = "<p>Y??u c???u ?????i m??n c???a b???n ???? b??? t??? ch???i</p>";
            case 3:
                html = "<p>M??n ??n c???a b???n ???? xong, m???i b???n xu???ng b???p ????? nh???n m??n</p>";
        }
        try {
            MailUtil.sendHtmlMail(this.javaMailSenderImpl, ownerRequest.getEmail(), "Th??ng b??o", html);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        request1.setStatus(status);
        requestService.save(request1);
        mv.addObject("msg", "success");
        return mv;
    }
}
