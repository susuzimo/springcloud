package org.wtm.openfeign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wtm.commons.User;

@Component
@RequestMapping("/wtm")   //防止请求地址重复
public class HelloServiceFallback implements HelloService {
    @Override
    public String hello() {
        return "errpr";
    }

    @Override
    public String hello2(String name) {
        return "error";
    }

    @Override
    public User user2(User user) {
        return null;
    }

    @Override
    public User user1(User user) {
        return null;
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public void user3(String name) {

    }
}
