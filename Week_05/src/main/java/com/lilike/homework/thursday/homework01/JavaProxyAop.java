package com.lilike.homework.thursday.homework01;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author llk
 * @Date 2020/11/16 20:09
 * @Version 1.0
 */
public class JavaProxyAop {

    public static void main(String[] args) {
        Cat cat = new Cat();
        Eat o = (Eat) Proxy.newProxyInstance(cat.getClass().getClassLoader(), cat.getClass().getInterfaces(),
                new MyCatProxy(cat));

        o.eat();
    }

}


interface Eat {

    void eat();
}


class Cat implements Eat {

    public void eat() {
        System.out.println("吃");
    }
}

class MyCatProxy implements InvocationHandler {

    private Cat cat;

    public MyCatProxy(Cat cat) {
        this.cat = cat;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("叫");
        method.invoke(cat,args);
        System.out.println("睡");
        return null;
    }
}
