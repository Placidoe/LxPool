package com.explorex.lxpool.entity.Pool;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO
 *
 * @Description
 * @Author Lx
 * @Date 2024/10/7 上午12:44
 **/
public class LxBootstrap {
    LxPool lxPool;
    HashMap<String,LxNode> nodeHashMap;//存储节点
    private static LxBootstrap lxBootstrap;
    private LxBootstrap() {}

    public static LxBootstrap getInstance() {
        if(lxBootstrap==null) {
            synchronized (LxBootstrap.class) {
                if(lxBootstrap==null)
                    lxBootstrap = new LxBootstrap();
            }
        }
        return new LxBootstrap();
    }

    public void init() {//初始化聚合池
    }

    public void start() {//启动聚合池

    }

    public void stop() {//关闭聚合池

    }

    public void addNode(String key,int offset) {//添加节点
        //1.新建节点

        //2.设置自处理函数

        //3.添加进聚合池里
//        nodeHashMap.put(key,lxNode);

    }

    //1.找下一个节点(设置查找方法)
    public LxNode getNextNode(String s, Long bitPos) {


    }
}
