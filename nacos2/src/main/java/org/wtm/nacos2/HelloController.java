package org.wtm.nacos2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {



    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        return restTemplate.getForObject("http://nacos1/hello",String.class);
    }



}
