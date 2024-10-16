package com.explorex.lxpool.entity.Pool;

import lombok.Data;

import java.util.HashMap;

/**
 * TODO
 *
 * @Description
 * @Author Lx
 * @Date 2024/10/6 下午5:19
 **/
public class LxPool {

    //1.聚合节点(支持快速索引-用hashMap)
    HashMap<String, LxNode> args;
    //2.用户id
    static ThreadLocal<Integer> userId;

    public static Integer getUserId() {
        return userId.get();
    }

}
