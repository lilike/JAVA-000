package com.lilike.homework.guardedsuspension;

import com.lilike.homework.guardedsuspension.guarded.GuardedObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author llk
 * @Date 2020/11/19 15:33
 * @Version 1.0
 */
@RestController
public class MessageController {

    @RequestMapping(value = "/onMessage")
    public void onMessage(@RequestParam(value = "uuid")String uuid,@RequestParam(value = "content")String content) {
        GuardedObject.fireEvent(uuid,content);
    }

}
