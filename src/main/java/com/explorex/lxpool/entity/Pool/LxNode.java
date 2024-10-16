package com.explorex.lxpool.entity.Pool;

import com.explorex.lxpool.entity.Observer.Observer;
import com.explorex.lxpool.entity.Observer.Subject;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * TODO
 *
 * @Description
 * @Author Lx
 * @Date 2024/10/6 下午5:48
 **/

@Data
public class LxNode implements Subject {
    public List<Object> args;//参数列表槽(v1，静态)
    public int nums;//所需的正确参数的个数
    public int id;//节点id
    public int State=0;//节点状态
    public MyFunction myFunction;//自处理函数，会拿到节点的参数列表判断是否已经满足
    //自处理函数的判断是否能处理业务的条件是，判断参数个数是否和初始化这个节点的参数的个数一致，如果参数个数一致了，就说明已经是达标了。
    public LxNode next;
    public LxNode left;
    public LxNode right;
    public CountDownLatch countDownLatch;


    public LxNode(HashMap<Object,Object> args, int NodeId,MyFunction function,int nums) {
        this.myFunction=function;
        this.nums=nums;
        this.id=NodeId;
    }

    public void putArgs(){

        //1.判断参数是否满足，如果满足，则去呼叫观察者执行自处理函数--todo 改成事件响应
        if(args.size()==nums){

        }
        //2.插入数据

        //1.判断参数是否满足，如果满足，则去呼叫观察者执行自处理函数--todo 改成事件响应
    }
    public Consumer<Object[]> test(){

        return null;
    }

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void unregisterObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }
}
