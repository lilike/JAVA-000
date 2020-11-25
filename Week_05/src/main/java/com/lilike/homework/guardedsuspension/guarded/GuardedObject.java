package com.lilike.homework.guardedsuspension.guarded;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @Author llk
 * @Date 2020/11/19 15:16
 * @Version 1.0
 */
public class GuardedObject<T,K> {

    // 受到保护的对象(也就是返回的消息)
    private T t;

    // 消息id == GuardedObject对象
    public static final ConcurrentHashMap<Object, GuardedObject> map = new ConcurrentHashMap<Object, GuardedObject>();

    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();

    final int timeout = 2;

    /**
     * 创建受保护的对象
     * @param k
     * @param <K>
     * @return
     */
    public  static  <K> GuardedObject create(K k) {
        GuardedObject guardedObject = new GuardedObject();
        map.put(k,guardedObject);
        return guardedObject;
    }

    // 获取到受保护对象
    public T get(Predicate<T> p) {
        lock.lock();
        try {
            while (!p.test(t)) {
                done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return t;
    }

    // 事件通知方法
    void onChanged(T obj) {
        lock.lock();
        try {
            this.t = obj;
            done.signalAll();
        }finally {
            lock.unlock();
        }
    }


    // 静态方法 用来通知时间
    public static <K,T> void fireEvent(K key,T obj) {
        GuardedObject object = map.remove(key);
        if (object != null) {
            object.onChanged(obj);
        }
    }

}
