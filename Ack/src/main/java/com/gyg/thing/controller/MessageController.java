package com.gyg.thing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by gyg
 * @date 2021/12/25 22:20
 * @description
 */
@RequestMapping("/message")
@RestController
public class MessageController {
    @PostMapping("/")
    public void inputMessage(String data){

    }
}
