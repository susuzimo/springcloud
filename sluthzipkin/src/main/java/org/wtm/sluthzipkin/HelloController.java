package org.wtm.sluthzipkin;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HelloController {
    private static final Log log= LogFactory.getLog(HelloController.class);

    @GetMapping("/hello")
    public String hello(String name){
        log.info("123");
        return "hello"+name;
    }
}
