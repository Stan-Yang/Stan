package com.stan.blogger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Article {

    @GetMapping("/blogger/index")
    public String index(){

        return "blogger/index";
    }

}
