package com.app.androidshop.po;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Long id;

    @Column(name = "camerist_id")
    private Long cameristId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_time")
    private String orderTime;

    @Column(name = "order_place")
    private String orderPlace;

    @Column(name = "orderDefineTime")
    private Date orderDefineTime;

    @Column(name = "order_status")
    private String status;
}
