package com.lhw.nacosorder.services;

import com.lhw.nacosapi.model.Order;

public interface IOrderService {

    Order getOrderInfo();

    Order getOrderInfoByNacos();

    Order getOrderInfoByNacosAndRibbon();

    Order getOrderInfoByNacosAndFeign();

}
