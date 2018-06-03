package com.app.androidshop.controller;

import com.app.androidshop.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    RegistController registController;

    @GetMapping("/findList")
    @ResponseBody
    public String findList() {
        System.out.println("======================>"+registController.getData());
        registController.add();
        List<String> strList = new ArrayList<>();
        strList.add("1");
        strList.add("2");
        strList.add("3");
        User test = new User();
        return strList.toString();
    }
}
