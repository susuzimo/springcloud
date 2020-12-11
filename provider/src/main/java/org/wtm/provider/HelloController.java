package org.wtm.provider;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.wtm.commons.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@RestController
public class HelloController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    @RateLimiter(name="rlA")
    public String hello(){
        String s="hello eureka"+port;
        System.out.println(new Date());
        return s;
    }

    @GetMapping("/hello2")
    public String hello2(String name){
        System.out.println("hello eureka"+name+new Date());
        return "hello eureka"+name+new Date();
    }

    @GetMapping("/hello3")
    public String hello3(String name,String age){
        return "hello eureka"+name+":"+age;
    }

    @PostMapping("/user1")
    public User addUser(User user){
        return  user;
    }

    @PostMapping("/user2")
    public User addUser2(@RequestBody User user){
        return  user;
    }


    @PutMapping("/user1")
    public void updateUser(User user){
        System.out.print(user);
    }

    @PutMapping("/user2")
    public void updateUser2(@RequestBody User user){
        System.out.print(user);

    }


    @DeleteMapping("/user1")
    public void deleteUser1(Integer id,Integer id1){
        System.out.println(id+id1);
    }



    @DeleteMapping("/user4")
    public  void user4(Integer id){
        System.out.println(id);

    }

    @DeleteMapping("/user5/{id}")
    public  void user5(@PathVariable Integer id){
        System.out.println(id);

    }

    @DeleteMapping("/user2/{id}/{id1}")
    public void deleteUser2(@PathVariable Integer id,@PathVariable Integer id1){
        System.out.println(id+id1);
    }


    @PutMapping("/user3/{id}")
    public void deleteUser3( @RequestBody User user, @PathVariable int id){
        System.out.println(user+""+id);
    }



    @DeleteMapping("/user3")
    public void deleteUser4(User user){
        System.out.println("delete"+user);
    }


    @GetMapping("/user3")
    public  void user3(@RequestHeader String name) throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode(name,"UTF-8"));

    }


}
