package org.wtm.sleuth;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    private static final Log log= LogFactory.getLog(HelloController.class);


    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public  String hello(){

        log.info("hello sleuth");
        return "hello sleuth";
    }


    @GetMapping("/hello2")
    public  String hello2() throws InterruptedException {

        log.info("hello sleuth2");
        Thread.sleep(500);
        return restTemplate.getForObject("http://localhost:1111/hello3",String.class);
    }

    @GetMapping("/hello3")
    public  String hello3(){
        log.info("hello sleuth3");
        return "hello sleuth";
    }



    @GetMapping("/hello4")
    public  String hello4(){
        log.info("hello sleuth4");
        return helloService.backgroundFun();
    }
}
