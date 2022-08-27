package com.epam.finalproject.framework.web.support.target;

import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.beans.annotation.Value;
import com.epam.finalproject.framework.web.annotation.Controller;
import com.epam.finalproject.framework.web.annotation.GetMapping;
import com.epam.finalproject.framework.web.annotation.RequestMapping;
import com.epam.finalproject.framework.web.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

@Controller
@RequestMapping("/test")
public class TestTargetController {

    @GetMapping("/1")
    public String test1(HttpServletRequest request){
        return "test1";
    }

    @GetMapping("/2")
    public String test2(HttpServletRequest request){
        return "test2";
    }

    @GetMapping("/{id}")
    public String testId(HttpServletRequest request,@RequestParam("id") Long id){
        return "testId";
    }
}
