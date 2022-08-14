package com.example.websitedatmon.controllers;

import com.example.websitedatmon.entity.Company;
import com.example.websitedatmon.serviceImpls.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class CompanyController {
    @Autowired
    CompanyServiceImpl companyService;

    @GetMapping({ "/company"})
    public ModelAndView index(String msg)
    {
        Company company = companyService.findCompanyById(1);
        ModelAndView mv = new ModelAndView("company");
        mv.addObject("msg",msg);
        mv.addObject("company",company);
        return mv;
    }

    @PostMapping(value = "/company-update")
    public ModelAndView update(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:company");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String diachi = request.getParameter("diachi");
        Company company = companyService.findCompanyById(1);
        company.setName(name);
        company.setEmail(email);
        company.setPhoneNumber(sdt);
        company.setAddress(diachi);
        companyService.save(company);
        mv.addObject("msg","success");
        return mv;
    }
}
