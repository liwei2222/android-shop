package com.app.androidshop.respository;

import com.app.androidshop.po.Production;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionRespository extends JpaRepository<Production,Long> {
    List<Production> findAllByCameristId(Long id);
}
