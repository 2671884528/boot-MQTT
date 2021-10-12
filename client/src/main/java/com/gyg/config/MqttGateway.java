package com.gyg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author by gyg
 * @date 2021/10/11 22:18
 * @description
 */
@MessagingGateway(defaultRequestChannel = MqSendConfig.OUTPUT_CHANNEL)
@Component
public interface MqttGateway {
    /**
     * <p>发送消息到MQTT</p>
     *
     * @param data  消息体
     * @param topic 队列名称
     */
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}
