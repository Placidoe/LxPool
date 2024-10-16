package com.explorex.lxpool.my_disruptor.User;


import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: 98050
 * @Time: 2018-12-19 16:23
 * @Feature:  相当于消费者，获取生产者推送过来的消息
 */
public class LoginEventHandler implements EventHandler<LoginEvent> {
 
 
    public void onEvent(LoginEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("消费者："+longEvent.getValue());
    }
}