package com.app.androidshop.service;

import com.app.androidshop.po.Production;
import com.app.androidshop.respository.ProductionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductionService {
    @Autowired
    private ProductionRespository respository;

    public List<String>  findAllByCameristId(Long id) {
        List<Production> productions = respository.findAllByCameristId(id);
        List<String> productionRoots = new ArrayList<>();
        for(Production production : productions) {
            productionRoots.add(production.getProduction());
        }
        return productionRoots;
    }

    public Production save(Production production) {
        return respository.save(production);
    }
}
