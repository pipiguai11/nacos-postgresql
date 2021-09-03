package com.lhw.nacosorder.services;

import com.lhw.nacosapi.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "nacos-user1")     //value值用于指定调用nacos下的哪个微服务
public interface IOrderUserFeignService {

    /**
     * 这里的写法就和controller里面一样，只要类上面定义了@FeignClient注解，然后这里面的接口方法上定义了Mapping注解
     * 就可以定位到服务地址
     * @FeignClient(value = "nacos-user1") + @GetMapping("/user/user/info") 就等于"http://nacos-user1/user/user/info"
     * @return
     */
    @GetMapping("/user/user/info")  //指定请求的URI
    User getUserInfo();

}
