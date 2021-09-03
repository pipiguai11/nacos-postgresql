package com.lhw.nacoscommodity.controller;

import com.lhw.nacosapi.model.Commodity;
import com.lhw.nacoscommodity.services.ICommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommodityController {

    @Autowired
    private ICommodityService commodityService;

    @GetMapping("commodity/info")
    public Commodity getCommdityInfo(){
        return commodityService.getCommdityInfo();
    }

}
