package com.lilike.homework;

/**
 * @Author llk
 * @Date 2020/11/9 20:43
 * @Version 1.0
 */
public class Factory {

    private int product = 100;

    public void produce() {
        synchronized (this) {
            if (this.product >= 100) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (this.product < 100) {
                product++;
                System.out.println("开始生产商品:" + product);
            }
            this.notifyAll();
        }
    }


    public void consume() {
        synchronized (this) {
            if (this.product < 1) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (this.product > 0) {
                product--;
                System.out.println("开始消费商品:" + product);
            }
            this.notifyAll();
        }

    }

    public static void main(String[] args) {
        Factory factory = new Factory();
        new Thread(() -> {
            while (true) {
                factory.produce();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                factory.consume();
            }
        }).start();

    }


}
