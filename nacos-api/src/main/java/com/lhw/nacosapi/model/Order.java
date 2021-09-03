package com.lhw.nacosapi.model;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private int orderId;
    private Date createTime;
    private String content;

    private String commodityName;
    private float price;

    private String userName;

}
