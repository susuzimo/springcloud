package org.wtm.consulconsumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/hello")
    public String hello(){

        String forObject = restTemplate.getForObject("http://127.0.0.1:2000/hello", String.class);

        return forObject+"consumer";
    }



    @GetMapping("/hello1")
    public String hello1(){
        ServiceInstance provider = loadBalancerClient.choose("consul-provider");
        String scheme = provider.getScheme();
        String forObject = restTemplate.getForObject(provider.getUri()+"/hello", String.class);
        return forObject+"consumer";
    }
}
