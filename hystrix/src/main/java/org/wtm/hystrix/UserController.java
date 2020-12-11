package org.wtm.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wtm.commons.User;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/hello11")
    public void user() throws ExecutionException, InterruptedException {
        //初始上下文
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        UserCollpaseCommand command1 = new UserCollpaseCommand(userService, 99);
        UserCollpaseCommand command2 = new UserCollpaseCommand(userService, 98);
        UserCollpaseCommand command3 = new UserCollpaseCommand(userService, 97);
        Future<User> queue = command1.queue();
        Future<User> queue1 = command2.queue();
        Future<User> queue2 = command3.queue();
        User user = queue.get();
        User u = queue1.get();
        User user2 = queue2.get();
        System.out.println(u+""+user+""+""+user2);
        Thread.sleep(2000);
        UserCollpaseCommand command4 = new UserCollpaseCommand(userService, 96);
        Future<User> queue3 = command4.queue();
        User user1 = queue3.get();
        System.out.println(user1);
        hystrixRequestContext.close();
    }

    @GetMapping("/hello12")
    public void user2() throws ExecutionException, InterruptedException {
        //初始上下文
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        Future<User> queue = userService.getByUserIds(99);
        Future<User> queue1 = userService.getByUserIds(98);
        Future<User> queue2 = userService.getByUserIds(97);
        Future<User> queue3 =userService.getByUserIds(96);
        User user = queue.get();
        User user1 = queue1.get();
        User user2 =queue2.get();
        User user3 =queue3.get();
        System.out.println(user);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        Thread.sleep(2000);
        Future<User> queue4 =userService.getByUserIds(95);
        User user4 =queue4.get();
        System.out.println(user4);
        hystrixRequestContext.close();
    }

}
