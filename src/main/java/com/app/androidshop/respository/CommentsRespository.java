package com.app.androidshop.respository;

import com.app.androidshop.po.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRespository extends JpaRepository<Comments,Long> {
     List<Comments> findAllByCameristId(Long cameristId);
}
