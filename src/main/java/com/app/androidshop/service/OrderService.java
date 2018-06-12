package com.app.androidshop.service;

import com.app.androidshop.po.Camerist;
import com.app.androidshop.po.Order;
import com.app.androidshop.po.User;
import com.app.androidshop.respository.OrderRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRespository respository;

    public Order save(Long cameristId, String orderTime, String orderPlace, HttpServletRequest request) {
        Order order = new Order();
        User ueser = (User)request.getSession().getAttribute("user");
        order.setCameristId(cameristId);
        order.setOrderPlace(orderPlace);
        order.setOrderTime(orderTime);
        order.setStatus("0");
        return respository.save(order);
    }

    public List<Order> fingAllByUserId(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        return respository.findAllByUserId(user.getId());
    }

    public List<Order> findAllByCameristId(HttpServletRequest request) {
        Camerist camerist = (Camerist)request.getSession().getAttribute("user");
        return respository.findAllByCameristId(camerist.getId());
    }

    public Boolean updateOrderStatus(Long id,String status) {
        Order order = respository.findAllById(id);
        if(order != null) {
            order.setStatus(status);
            order.setOrderDefineTime(new Date());
        }
        return true;
    }

    public Order findOrderById(Long id) {
        return  respository.findAllById(id);
    }



}
