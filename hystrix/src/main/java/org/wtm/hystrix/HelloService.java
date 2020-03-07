package org.wtm.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class HelloService {


    @Autowired
    RestTemplate restTemplate;

    /*
    在这个方法中，我们去远程调用一个方法。可能会因为某些原因失败
    我们在方法上添加@HystrixCommand注解 配置fallbackMethod属性，这个属性表示该方法调用失败时的临时替代方法
     */

    //ignoreException 忽略异常
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    public String hello(){
        int i=1/0;
        return  restTemplate.getForObject("http://provider/hello",String.class);
    }


    //通过注解异步
    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2(){

        return  new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/hello",String.class);
            }
        };

    }


    /*
    注意，这个方法名字要和fallbackMethod一致，返回值也要一致
     */
  // @HystrixCommand(fallbackMethod = "error2")   //一级一级往下
    public String error(Throwable throwable){
         return  "error"+throwable.getMessage();
    }





    @HystrixCommand(fallbackMethod = "error2")
    @CacheResult    //@CacheKey 缓存单个key
    public String hello3(String name){
        return  restTemplate.getForObject("http://provider/hello2?name={1}",String.class,name);
    }

    public String error2(String name){
        return  "error=====请求缓存";
    }

}
