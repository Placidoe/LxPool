package com.explorex.lxpool.entity.Observer;

public interface Subject {
    //1.注册观察者
    void registerObserver(Observer observer);
    //2.移除观察者
    void unregisterObserver(Observer observer);
    //3.唤醒观察者
    void notifyObservers();
}