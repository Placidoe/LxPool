package com.explorex.lxpool.api;

import com.explorex.lxpool.entity.Pool.LxBootstrap;
import com.explorex.lxpool.entity.Pool.LxNode;
import com.explorex.lxpool.entity.Pool.LxPool;
import com.explorex.lxpool.entity.User.User;
import com.explorex.lxpool.my_disruptor.User.LoginEvent;
import com.explorex.lxpool.my_disruptor.User.LoginEventFactory;
import com.explorex.lxpool.my_disruptor.User.LoginEventHandler;
import com.explorex.lxpool.my_disruptor.User.LoginEventProducer;
import com.explorex.lxpool.utils.BitmapService;
import com.explorex.lxpool.utils.SnowflakeIdGenerator;
import com.explorex.lxpool.utils.SnowflakeIdUtil;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import jakarta.annotation.Resource;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 *
 * @Description
 * @Author Lx
 * @Date 2024/10/12 下午8:56
 **/
public class UserController implements AbstractPutArgs{
    @Resource
    BitmapService bitmapService;

    AtomicInteger atomicInteger;


    static LxBootstrap lxBootstrap=LxBootstrap.getInstance();


    //1.创建一个可缓存的线程池 提供线程来出发Consumer 的事件处理
    static ExecutorService executorService = Executors.newCachedThreadPool();
    //2.创建Event工厂
    static EventFactory<LoginEvent> eventEventFactory = new LoginEventFactory();
    //3.设置ringBuffer大小
    static int ringBufferSize = 1024 * 1024;
    //4.创建Disruptor，单生产者模式，消费者等待策略为YieldingWaitStrategy
    static Disruptor<LoginEvent> disruptor = new Disruptor<LoginEvent>(eventEventFactory, ringBufferSize, executorService, ProducerType.SINGLE,new YieldingWaitStrategy());
    //7.创建RingBuffer容器
    static RingBuffer<LoginEvent> ringBuffer = disruptor.getRingBuffer();
    //8.创建生产者
    static LoginEventProducer producer = new LoginEventProducer(ringBuffer);
    static {
        //5.注册消费者
        disruptor.handleEventsWith(new LoginEventHandler());
        //6.启动Disruptor
        disruptor.start();
    }

    public void SignUp(String name,String password) {
        //校验用户是否存在--todo
//        atomicInteger.incrementAndGet();

        //1.为用户分配id-雪花算法
        long id = SnowflakeIdUtil.nextId();
        //2.用户落库--todo 待落库
        User user = new User();
        user.setId(id);


        for(int i=0;i<10;i++){
            //3.用户分配bitmap，分配10个标记点
            bitmapService.setBit("user:"+id,i,false);
            //4.用户分配聚合池node，分配10个节点
            lxBootstrap.addNode("user:"+id,i);
        }
    }

    @Override
    public void putArgs() {
        ////下面这部分放入到disruptor中去--TODO 貌似需要改成现成的MQ
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
//        producer.onData();
        //1.先去找第一个bitmap的标记
        Long bitPos = bitmapService.bitPos("user:"+LxPool.getUserId(), false);
        bitmapService.setBit("user:"+LxPool.getUserId(),bitPos,true);
        //2.再去找一个node
        LxNode node = lxBootstrap.getNextNode("user:"+LxPool.getUserId(),bitPos);
        //3.
    }
}
