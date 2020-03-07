package org.wtm.provider;

import org.wtm.api.IUserService;
import org.wtm.commons.User;

public class IHelloController implements IUserService {
    @Override
    public String hello() {
        //进行操作 和helloController一样
        return null;
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
}
