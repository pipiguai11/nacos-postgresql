package com.lhw.nacosorder.services;

import com.lhw.nacosapi.model.Commodity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "nacos-commodity1")
public interface IOrderCommodityFeignService {

    @GetMapping("/comm/commodity/info")
    Commodity getCommodityInfo();

}
