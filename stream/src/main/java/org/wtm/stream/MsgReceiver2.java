package org.wtm.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

//绑定消息通道
@EnableBinding(MyChannel.class)
public class MsgReceiver2 {

    @StreamListener(MyChannel.INPUT)
    public  void receive(Object payload){

        System.out.println("Receive:"+payload);
    }
}
