package com.app.androidshop.po;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="camerist")
public class Camerist {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "note")
    private String note;

    @Column(name = "location")
    private  String location;

    @Column(name = "phone")
    private String phone;

    @Column(name = "price")
    private String price;
}
