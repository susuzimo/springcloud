package org.wtm.api;


import org.springframework.web.bind.annotation.*;
import org.wtm.commons.User;

public interface IUserService {

    @GetMapping("/hello")
    String hello();  //这里的方法名随便取

    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);


    @PostMapping("/user2")
    User user2(@RequestBody User user);


    @PostMapping("/user1")
    User user1(User user);

    @DeleteMapping("/user5/{id}")
    void deleteUser(@PathVariable("id") Integer id);


    @GetMapping("/user3")
    void user3(@RequestHeader("name") String name);


}
