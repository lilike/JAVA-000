package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.client.CuratorClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.*;

import java.io.InputStream;

/**
 * @Author llk
 * @Date 2020/12/24 17:01
 * @Version 1.0
 */
public class CuratorDemo {

    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorClient.getClient();

        CuratorCache cache = CuratorCache.build(client,"/");

        CuratorCacheListener curatorCacheListener = CuratorCacheListener.builder().
                        forPathChildrenCache("/rpcfx", client, new PathChildrenCacheListener() {
                            @Override
                            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                                // 首先获取到value 代表这是一个什么 比如service 表示的就是服务 如果是 host 表示的是主机名
                                String value = new String(event.getData().getData());

                                // 找到这个provider的父类
                                PathChildrenCacheEvent.Type type = event.getType();
                                String path = event.getData().getPath();

                                String[] split = path.split("/");
                                path = split[split.length-1];
                                if (value.equals("service")) {
                                    if (type.equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                                        System.out.println("新增了" + path + "服务！");
                                    }else if (type.equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                                        System.out.println("删除了" + path + "服务！");
                                    }
                                }else if (value.equals("provider")) {

                                    if (type.equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                                        System.out.println("主机" + path + "上线！");
                                    }else if (type.equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                                        System.out.println("主机" + path + "下线！");
                                    }
                                }
                            }
                        }).build();

        cache.listenable().addListener(curatorCacheListener);
        cache.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

}
