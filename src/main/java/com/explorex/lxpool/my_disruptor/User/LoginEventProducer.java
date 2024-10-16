package com.explorex.lxpool.my_disruptor.User;


import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @Author: 98050
 * @Time: 2018-12-19 16:27
 * @Feature:
 */

public class LoginEventProducer {

    public final RingBuffer<LoginEvent> ringBuffer;

    public LoginEventProducer(RingBuffer<LoginEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
 
    public void onData(ByteBuffer byteBuffer){
        //1.获取ringBuffer的下标位置
        long sequence = ringBuffer.next();
        Long data = null;
 
        //2.取出ringBuffer中的空位置
        LoginEvent longEvent = ringBuffer.get(sequence);
        //3.然后赋值
        data = byteBuffer.getLong(0);
        longEvent.setValue(data);
 
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("生产者准备发送数据");
            //4.发送数据
            ringBuffer.publish(sequence);//put进去，并且唤醒等待的消费者
        }
    }
}