package com.app.androidshop.respository;

import com.app.androidshop.po.Camerist;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CameristRespository extends JpaRepository<Camerist,Long> {

    Camerist findAllByNameAndPassword(String name,String passWord);

    Camerist findAllByName(String name);

    List<Camerist> findAll();

    Camerist findAllById(Long id);


}
