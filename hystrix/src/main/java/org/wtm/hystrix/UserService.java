package org.wtm.hystrix;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.wtm.commons.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCollapser(batchMethod = "getByIds",collapserProperties = {@HystrixProperty(name="timerDelayInMilliseconds",value = "200")})
    public Future<User>  getByUserIds(Integer id){
        return  null;
    }


    //使用注解的方式  getByIds  需要加上注解

    @HystrixCommand
    public List<User> getByIds(List<Integer> ids){
        System.out.println(ids);
        User[] forObject = restTemplate.getForObject("http://provider/user11/{1}", User[].class, StringUtils.join(ids, ","));
        return Arrays.asList(forObject);
    }


}
