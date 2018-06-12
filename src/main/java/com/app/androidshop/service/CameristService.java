package com.app.androidshop.service;

import com.app.androidshop.po.Camerist;
import com.app.androidshop.po.User;
import com.app.androidshop.respository.CameristRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class CameristService {

    @Autowired
    private CameristRespository respository;

    public Camerist save(Camerist camerist) {
        return respository.save(camerist);
    }

    public Boolean findCameristByName(String name) {
        if(respository.findAllByName(name) == null) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean login(String name, String password, HttpServletRequest request) {
        Camerist camerist = respository.findAllByNameAndPassword(name,password);
        if(camerist != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user",camerist);
            return true;
        }
        return false;
    }
}
