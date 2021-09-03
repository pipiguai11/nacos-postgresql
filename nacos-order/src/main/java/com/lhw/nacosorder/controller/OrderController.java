package com.lhw.nacosorder.controller;

import com.lhw.nacosapi.model.Order;
import com.lhw.nacosorder.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/order/info")
    public Order getOrderInfo(){
        return orderService.getOrderInfo();
    }

    @GetMapping("/order/nacos/info")
    public Order getOrderInfoByNacos(){
        return orderService.getOrderInfoByNacos();
    }

    @GetMapping("/order/ribbon/info")
    public Order getOrderInfoByNacosAndRibbon(){
        return orderService.getOrderInfoByNacosAndRibbon();
    }

    @GetMapping("/order/feign/info")
    public Order getOrderInfoByNacosAndFeign(){
        return orderService.getOrderInfoByNacosAndFeign();
    }

}
