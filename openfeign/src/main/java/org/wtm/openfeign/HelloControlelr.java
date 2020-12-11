package org.wtm.openfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wtm.commons.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
public class HelloControlelr {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello(){

        return helloService.hello();
    }

    @GetMapping("/hello1")
    public String hello1(){
        return helloService.hello2("suzimo");
    }

    @GetMapping("/hello2")
    public User hello2(){
        User user=new User();
        user.setId(1);
        return helloService.user2(user);
    }


    @GetMapping("/hello4")
    public User hello4(){
        User user=new User();
        user.setId(1);
        return helloService.user1(user);
    }

    @GetMapping("/hello3")
    public void hello3() throws UnsupportedEncodingException {
        helloService.user3(URLEncoder.encode("苏子茉","UTF-8"));
    }
}
