package org.wtm.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private static final Log log= LogFactory.getLog(HelloService.class);

    @Async
    public String backgroundFun(){

        log.info("backgroundFun");
        return "backgroundFun";
    }



    @Scheduled(cron = "0/10 * * * * ?")
    public void sch1(){
        log.info("start");
        backgroundFun();
        log.info("end");


    }


}
