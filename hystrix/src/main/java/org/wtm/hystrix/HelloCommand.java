package org.wtm.hystrix;

//请求命令

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

public class HelloCommand extends HystrixCommand<String>{

    RestTemplate restTemplate;

    public HelloCommand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        int i=1/0;
        return restTemplate.getForObject("http://provider/hello",String.class);
    }

    //请求失败的回调  容错/降级
    @Override
    protected String getFallback() {

        return "error------"+getExecutionException().getMessage();
    }
}
