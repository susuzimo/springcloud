package org.wtm.zipkin2;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    private static final Log log= LogFactory.getLog(HelloController.class);
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello1")
    public  String hello(){
        log.info("345");
        return restTemplate.getForObject("http://localhost:1234/hello?name={1}",String.class,"suzimo");
    }
}
