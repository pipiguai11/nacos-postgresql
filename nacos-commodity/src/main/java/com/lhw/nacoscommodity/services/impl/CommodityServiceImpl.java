package com.lhw.nacoscommodity.services.impl;

import com.lhw.nacosapi.model.Commodity;
import com.lhw.nacoscommodity.services.ICommodityService;
import org.springframework.stereotype.Service;

@Service
public class CommodityServiceImpl implements ICommodityService {
    @Override
    public Commodity getCommdityInfo() {
        System.out.println("调用了这个服务");
        Commodity commodity = new Commodity();
        commodity.setCommName("衣服");
        commodity.setCommPrice(1000.0f);
        commodity.setContent("这衣服真好看");
        return commodity;
    }
}
