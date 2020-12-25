package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Author llk
 * @Date 2020/12/25 14:23
 * @Version 1.0
 */
public class CuratorClient {

    private static CuratorFramework client;

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder().connectString("localhost:2181").namespace("rpcfx").retryPolicy(retryPolicy).build();
        client.start();
    }

    public static CuratorFramework getClient() {
        return client;
    }

}
