package com.app.androidshop.respository;

import com.app.androidshop.po.Camerist;
import com.app.androidshop.po.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRespository extends JpaRepository<User,Long> {

    User findAllByNameAndPassword(String name,String passWord);

    User findAllByName(String name);

}
