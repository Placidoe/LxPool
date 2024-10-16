package com.explorex.lxpool.my_disruptor.User;


import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 98050
 * @Time: 2018-12-19 16:34
 * @Feature:
 */
public class DisruptorMain {
 
    public static void main(String[] args) {
        //1.创建一个可缓存的线程池 提供线程来出发Consumer 的事件处理
        ExecutorService executorService = Executors.newCachedThreadPool();
        //2.创建Event工厂
        EventFactory<LoginEvent> eventEventFactory = new LoginEventFactory();
        //3.设置ringBuffer大小
        int ringBufferSize = 1024 * 1024;
        //4.创建Disruptor，单生产者模式，消费者等待策略为YieldingWaitStrategy
        Disruptor<LoginEvent> disruptor = new Disruptor<LoginEvent>(eventEventFactory, ringBufferSize, executorService, ProducerType.SINGLE,new YieldingWaitStrategy());
        //5.注册消费者
        disruptor.handleEventsWith(new LoginEventHandler());
        //可以配置多个消费者，一个生产者 默认重复消费，配置分组
 
        //6.启动Disruptor
        disruptor.start();
        //7.创建RingBuffer容器
        RingBuffer<LoginEvent> ringBuffer = disruptor.getRingBuffer();
        //8.创建生产者
        LoginEventProducer producer = new LoginEventProducer(ringBuffer);
        //9.指定缓冲区的大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (int i = 0; i < 100; i++) {
            //10.将i放在第0个位置
            byteBuffer.putLong(0,i);
            producer.onData(byteBuffer);
        }
        disruptor.shutdown();
        executorService.shutdown();
    }
}