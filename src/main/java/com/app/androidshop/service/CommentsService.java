package com.app.androidshop.service;

import com.app.androidshop.po.Comments;
import com.app.androidshop.po.User;
import com.app.androidshop.respository.CommentsRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
@Slf4j
public class CommentsService {

    @Autowired
    private CommentsRespository respository;

    public Comments save(String comment, Long cameristId,HttpServletRequest request) {
        Comments comments = new Comments();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user != null) {
            comments.setCameristId(cameristId);
            comments.setComment(comment);
            comments.setCommentTime(new Date());
            comments.setUserId(user.getId());
            comments.setUserName(user.getName());
        }
        return respository.save(comments);
    }

}
