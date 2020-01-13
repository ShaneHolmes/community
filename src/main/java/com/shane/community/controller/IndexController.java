package com.shane.community.controller;

import com.shane.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping({"/index","/"})
    public String index(HttpServletRequest request){
        indexService.index(request);
        return "index";
    }
}
