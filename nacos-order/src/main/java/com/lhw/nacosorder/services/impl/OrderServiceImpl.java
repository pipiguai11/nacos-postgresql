package com.lhw.nacosorder.services.impl;

import com.lhw.nacosapi.model.Commodity;
import com.lhw.nacosapi.model.Order;
import com.lhw.nacosapi.model.User;
import com.lhw.nacosorder.services.IOrderCommodityFeignService;
import com.lhw.nacosorder.services.IOrderService;
import com.lhw.nacosorder.services.IOrderUserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier(value = "restTemplate2")
    private RestTemplate restTemplate2;

    /**
     * 这两个个Service是通过Feign方式定义的，因此可以直接像调用本地服务一样注入调用
     */
    @Autowired
    private IOrderUserFeignService orderUserFeignService;
    @Autowired
    private IOrderCommodityFeignService orderCommodityFeignService;

    /**
     * 这个DiscoveryClient对象是nacos客户端提供的一个对象，也就是在jar包中带过来的
     *      通过这个对象，我们可以拿到nacos中的服务列表信息
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public Order getOrderInfo() {
        /**
         * 这种方式定死了调用的服务，不够灵活
         */
        User user = restTemplate.getForObject("http://localhost:8081/user/user/info", User.class);
        Commodity comodity = restTemplate.getForObject("http://localhost:8061/comm/commodity/info", Commodity.class);

        return this.setOrderValue(user,comodity);
    }

    @Override
    public Order getOrderInfoByNacos(){
        /**
         * 通过discoveryClient对象的getInstance方法可以获取到nacos上的服务列表
         *      传进去一个服务名（这个服务名指的是我们配置文件中配置的spring.application.name的值）
         *      然后就可以获取到一个list，为什么是个list呢？
         *      因为在大型项目中，可能会存在集群，也就是同时部署了多套同一个服务在不同的服务器上
         *      然后这个discoveryClient就会根据服务名，来获取注册到nacos上的所有的服务列表
         *      每个ServiceInstance中都包含服务id（也就是服务名），IP，端口等信息
         */
        List<ServiceInstance> commodityInstances = discoveryClient.getInstances("nacos-commodity1");
        ServiceInstance commodityServiceInstance = commodityInstances.get(0);
        List<ServiceInstance> userInstances = discoveryClient.getInstances("nacos-user1");
        /**
         * 负载均衡处理
         *
         * 注意了：
         *      如果这里和上面一样也是直接取第一个ServiceInstance的话，那是有问题的
         *      因为在生产环境中，可能会存在多个相同的服务，也就是集群部署了，那这时一直调用第一个服务显然是不合理的
         *      我们需要实现负载均衡的策略，动态的调用服务
         *      怎么实现呢？
         *      很简单，只需要修改调用的ServiceInstance即可。如下
         *
         *  当然，以下这种方式只是能够实现简单的随机策略的负载均衡，但是无法实现轮询等策略的负载均衡
         *  因此，我们还是需要引入另一个中间件去专门做这件事
         */
        int index = new Random().nextInt(userInstances.size());
        ServiceInstance userServiceInstance = userInstances.get(index);

        User user = restTemplate.getForObject("http://"+ userServiceInstance.getHost() +":" + userServiceInstance.getPort() + "/user/user/info", User.class);
        Commodity comodity = restTemplate.getForObject("http://"+ commodityServiceInstance.getHost() +":" + commodityServiceInstance.getPort() + "/comm/commodity/info", Commodity.class);

        return this.setOrderValue(user,comodity);
    }

    /**
     * 采用ribbon做负载均衡
     *      这里不需要再通过Host和Port去指定服务地址了
     *      我们可以直接通过服务ID（也就是服务名进行绑定）
     *      它会根据服务ID自动找到nacos上注册的服务列表，然后在列表中根据负载均衡策略选择一个服务进行调用。
     *      这个策略是我们可以在配置中进行配置的
     * @return
     */
    @Override
    public Order getOrderInfoByNacosAndRibbon() {
        String commodityServiceId = "nacos-commodity1";
        String userServiceId = "nacos-user1";

        User user = restTemplate2.getForObject("http://"+ userServiceId + "/user/user/info", User.class);
        Commodity comodity = restTemplate2.getForObject("http://"+ commodityServiceId + "/comm/commodity/info", Commodity.class);

        return this.setOrderValue(user,comodity);
    }

    /**
     * 通过Feign的方式调用远程服务
     *      看前面的代码，发现一直使用的都是restTemplate的方式远程调用服务。这样存在两个问题
     *         1、代码可读性差
     *         2、代码风格不一致
     *    为了解决这两个问题，可以使用Feign的方式进行远程服务的调用
     *    在这个服务中定义一个接口并绑定好需要调用的远程服务地址url
     *    然后就可以像调用本地服务一样调用远程服务了
     * @return
     */
    @Override
    public Order getOrderInfoByNacosAndFeign() {

        User user = orderUserFeignService.getUserInfo();
        Commodity comodity = orderCommodityFeignService.getCommodityInfo();

        return this.setOrderValue(user,comodity);
    }



    private Order setOrderValue(User user, Commodity commodity){
        Order order = new Order();
        order.setCommodityName(commodity.getCommName());
        order.setPrice(commodity.getCommPrice());
        order.setUserName(user.getName());
        order.setOrderId(1);
        order.setContent("yyyyy");
        order.setCreateTime(new Date());
        return order;
    }


}
