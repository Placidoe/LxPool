package com.explorex.lxpool.my_disruptor.User;


import com.lmax.disruptor.EventFactory;

/**
 * @Author: 98050
 * @Time: 2018-12-19 16:22
 * @Feature: 实例化LongEvent
 */
public class LoginEventFactory implements EventFactory<LoginEvent> {
 
    public LoginEvent newInstance() {
        return new LoginEvent();
    }
}