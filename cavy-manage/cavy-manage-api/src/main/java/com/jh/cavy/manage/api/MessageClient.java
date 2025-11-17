package com.jh.cavy.manage.api;


import com.jh.cavy.manage.vo.MessageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/message")
@RestController
@FeignClient(name = "cavy-manage", contextId = "messageClient", path = "message")
public interface MessageClient {
    @PostMapping
    List<MessageVO> messageList();
    @GetMapping("/{id}")
    MessageVO getById(@PathVariable("id") String id);
    @PutMapping("/readed")
    void readedMessage(@RequestParam("id") String id);
    @PutMapping("/readedAll")
    void readedAll();
}
