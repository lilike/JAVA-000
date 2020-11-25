package com.lilike.homework.threadlocaloom;

/**
 * @Author llk
 * @Date 2020/11/19 16:16
 * @Version 1.0
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 500 个线程 每个线程放很多的对象
 *
 *
 *
 */
public class ThreadLocalOOMDemo {

    private static final int THREAD_LOOP_SIZE = 500;
    private static final int MOCK_DIB_DATA_LOOP_SIZE = 10000;

    private static ThreadLocal<List<User>> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_LOOP_SIZE);

        for (int i = 0; i < THREAD_LOOP_SIZE; i++) {
            executorService.execute(() -> {
                // 内存有限,线程过多,线程池里面的线程不会释放掉,所以里面的  ThreadLocal.ThreadLocalMap.get(this.ThreadLocal)也无法释放
                threadLocal.set(new ThreadLocalOOMDemo().addBigList()); // 这一步其实可以替换的,替换之后的对象可以被回收,但是500个线程,没有remove() 所以不会回收掉,所以就OOM了
                Thread t = Thread.currentThread();

                System.out.println(Thread.currentThread().getName());
                //threadLocal.remove(); //不取消注释的话就可能出现OOM
            });
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //executorService.shutdown();
    }

    private List<User> addBigList() {
        List<User> params = new ArrayList<>(MOCK_DIB_DATA_LOOP_SIZE);
        for (int i = 0; i < MOCK_DIB_DATA_LOOP_SIZE; i++) {
            params.add(new User("xuliugen", "password" + i, "男", i));
        }
        return params;
    }

    class User {
        private String userName;
        private String password;
        private String sex;
        private int age;

        public User(String userName, String password, String sex, int age) {
            this.userName = userName;
            this.password = password;
            this.sex = sex;
            this.age = age;
        }
    }
}
