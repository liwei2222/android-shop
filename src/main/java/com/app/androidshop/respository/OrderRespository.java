package com.app.androidshop.respository;

import com.app.androidshop.po.Camerist;
import com.app.androidshop.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRespository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserId(Long id);

    List<Order> findAllByCameristId(Long id);

    Order findAllById(Long id);
}
