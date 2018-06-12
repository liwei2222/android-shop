package com.app.androidshop.po;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "productions")
public class Production {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Long id;

    @Column(name = "camerist_id")
    private Long cameristId;

    @Column(name = "production")
    private String production;

}
