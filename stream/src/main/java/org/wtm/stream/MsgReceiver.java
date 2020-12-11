package org.wtm.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

//绑定消息通道  Sink默认的定义
@EnableBinding(Sink.class)
public class MsgReceiver {

    @StreamListener(Sink.INPUT)
    public  void receive(Object payload){
        System.out.println("Receive:"+payload);
    }
}
