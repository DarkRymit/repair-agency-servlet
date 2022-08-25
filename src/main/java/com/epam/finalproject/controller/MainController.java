package com.epam.finalproject.controller;

import com.epam.finalproject.framework.web.annotation.Controller;
import com.epam.finalproject.framework.web.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    String mainPage() {
        return "index";
    }
}
