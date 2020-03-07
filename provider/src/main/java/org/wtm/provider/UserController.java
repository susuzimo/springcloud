package org.wtm.provider;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.wtm.commons.User;

import java.util.ArrayList;
import java.util.List;

//请求合并
@RestController
public class UserController {



    //单个查询
    public User user(Integer id){
        User user=new User();
        user.setId(id);
        return  user;
    }


    @GetMapping("/user11/{ids}")
    public List<User> getByIds(@PathVariable String ids){
        String[] split = ids.split(",",-1);
        List<User> users=new ArrayList<>();
        for(String s:split){
            User user=new User();
            user.setId(Integer.parseInt(s));
            users.add(user);
        }
        return users;

    }

}



