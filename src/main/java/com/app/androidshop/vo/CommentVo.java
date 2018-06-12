package com.app.androidshop.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {
    private String userName;
    private String context;
    private Date commentDate;
}
