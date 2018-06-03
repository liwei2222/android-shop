package com.app.androidshop.respository;

import com.app.androidshop.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Long> {

}
