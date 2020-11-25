package com.lilike.thursday.guava.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.lilike.thursday.domain.Person;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author llk
 * @Date 2020/11/21 12:19
 * @Version 1.0
 */
public class EventBusDemo {

    public static void main(String[] args) {

//        AsyncEventBus eventBus = new AsyncEventBus("无名EventBus", Executors.newCachedThreadPool());
        EventBus eventBus = new EventBus();
        eventBus.register(new PersonNotify());
        System.out.println("处理业务逻辑,然后你通知下理可");
        eventBus.post(new Person(1,"理可"));
        System.out.println("主线程结束");
    }


}

class PersonNotify {

    @Subscribe
    public void notifyPerson(Person p) {
        System.out.println( Thread.currentThread().getName() + "--" + p.getName() + "好的,我收到了");
    }

}
