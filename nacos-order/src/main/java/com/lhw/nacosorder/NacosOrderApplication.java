package com.lhw.nacosorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient  //开启nacos，使得服务可以被注册到nacas上
@EnableFeignClients  //开启feign的客户端
public class NacosOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosOrderApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 配置ribbon调用时负载均衡策略
     * @return
     */
    @Bean("restTemplate2")
    @LoadBalanced
    public RestTemplate restTemplate2(){
        return new RestTemplate();
    }

}
