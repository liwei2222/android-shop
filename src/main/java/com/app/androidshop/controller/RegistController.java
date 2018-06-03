package com.app.androidshop.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class RegistController {
    private int i =0;
    public int getData() {
        return i;
    }
    public  void setData(int i) {
        this.i = i;
    }
    public int add() {
        return i++;
    }
}
