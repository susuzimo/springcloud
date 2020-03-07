package org.wtm.hystrix;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.wtm.commons.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//三个参数   批处理返回的类型  数据响应的类型 参数的类型
public class UserCollpaseCommand  extends HystrixCollapser<List<User>,User,Integer>{

    private  UserService userService;
    private  Integer  id;

    public UserCollpaseCommand(UserService userService, Integer id) {                                                                                                                    //延迟多少时间 200ms以内
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollpaseCommand")).andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200)));
        this.userService = userService;
        this.id = id;
    }

    /*
        请求参数
         */
    @Override
    public Integer getRequestArgument() {
        return id;
    }


    /*
    请求合并的方法
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collection) {

        List<Integer> ids=new ArrayList<>(collection.size());
        for(CollapsedRequest<User, Integer> collections:collection){
            ids.add(collections.getArgument());
        }

        return new UserBatchCommand(ids,userService);
    }


    /*
    请求结果分发
     */
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection) {

        int count=0;
        for(CollapsedRequest<User, Integer> collapsedRequest:collection){
            collapsedRequest.setResponse(users.get(count++));
        }
    }
}
