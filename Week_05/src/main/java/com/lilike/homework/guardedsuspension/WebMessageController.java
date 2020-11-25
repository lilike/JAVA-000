package com.lilike.homework.guardedsuspension;

import com.lilike.homework.guardedsuspension.domain.Message;
import com.lilike.homework.guardedsuspension.guarded.GuardedObject;
import com.lilike.homework.guardedsuspension.utils.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**

    有这样一个场景
        服务端接收到用户端的请求,服务端线程池T1接收用户的请求,并发送一个消息到消息中间件

        然后其他服务去中间件抓取这个消息,然后将结果返回到消息中间件

        当前的服务端可以在onMessage方法中接收到这个消息,但是onMessage接收到的消息已经是其他的线程了

        但是接收到消息需要T1线程去处理

        如何做?

 * @Author llk
 * @Date 2020/11/19 15:02
 * @Version 1.0
 */
@RestController
public class WebMessageController {

    @RequestMapping(value = "/handleRequest")
    public void handleRequest(@RequestParam(value = "message") String message) {

        // 生成一条消息
        String uuid = UUIDUtils.getUUID();
        Message msg = new Message();
        msg.setId(uuid);
        msg.setContent(message);

        // 发送消息
        // send message

        System.out.println("uuid :" + uuid);

        // 保护性暂停 Guarded Suspension
        // 互联网里面任何问题都可以通过增加一个中间层来解决
        GuardedObject guardedObject = GuardedObject.create(uuid);

        // 等待消息队列返回结果
        String o = (String) guardedObject.get(t -> t != null);
        System.out.println(o);
    }
}

