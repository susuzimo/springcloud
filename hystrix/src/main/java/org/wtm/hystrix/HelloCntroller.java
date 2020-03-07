package org.wtm.hystrix;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloCntroller {


    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        return helloService.hello();
    }

    @GetMapping("/hello1")
    public String hello1(){
        HelloCommand javaboy = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy")), restTemplate);
        String execute = javaboy.execute();
        System.out.println(execute);

        //一个对象只能执行一次
        HelloCommand javaboy1 = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy")), restTemplate);
        Future<String> queue = javaboy1.queue();
        try {
            String s = queue.get();  //先入对后执行
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return execute;
    }



    @GetMapping("/hello3")
    public void Hello(){
        Future<String> stringFuture = helloService.hello2();
        try {
            String s = stringFuture.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/hello4")
    public String hello4(){
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        String s = helloService.hello3("1111");
        String sq = helloService.hello3("1111");
        hystrixRequestContext.close();
        return s;

    }

}
