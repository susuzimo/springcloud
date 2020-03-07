package org.wtm.openfeign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.wtm.commons.User;

@Component
public class HelloServiceFallabckF implements FallbackFactory<HelloService> {
    @Override
    public HelloService create(Throwable throwable) {
        return new HelloService() {
            @Override
            public String hello() {
                return "error111111111111111";
            }

            @Override
            public String hello2(String name) {
                return null;
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
        };
    }
}
