package com.gyg.controller;

import com.gyg.config.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by gyg
 * @date 2021/10/11 22:24
 * @description
 */

@RestController
@RequestMapping("/")
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;

    @GetMapping("/")
    public String sendMessage(@RequestParam(name = "message") String message, @RequestParam(name = "topic") String topic) {
        mqttGateway.sendToMqtt(message, topic);
        return "OK";
    }
}
