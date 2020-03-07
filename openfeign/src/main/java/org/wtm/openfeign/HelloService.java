package org.wtm.openfeign;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.wtm.commons.User;

import java.util.Map;

/*
@FeignClient(value = "provider",fallback = HelloServiceFallback.class)
*/
//第二种
@FeignClient(value = "provider",fallbackFactory = HelloServiceFallabckF.class)
public interface HelloService {

    @GetMapping("/hello")
    String hello();  //这里的方法名随便取

    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);


    @PostMapping("/user2")
    User user2(@RequestBody User user);


    @PostMapping("/user1")
    User user1(@RequestParam("user")User user);

    @DeleteMapping("/user5/{id}")
    void deleteUser(@PathVariable("id") Integer id);


    @GetMapping("/user3")
    void user3(@RequestHeader("name") String name);

}
