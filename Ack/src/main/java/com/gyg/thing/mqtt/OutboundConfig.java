package com.gyg.thing.mqtt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @author by gyg
 * @date 2021/12/25 22:42
 * @description
 */
@Configuration
public class OutboundConfig {

    public static final String OUTBOUND_CHANNEL = "outboundChannel";
    public static final String CHANNEL_HANDLER = "channelHandler";

    private final MqttFactory mqttFactory;

    public OutboundConfig(MqttFactory mqttFactory) {
        this.mqttFactory = mqttFactory;
    }

    @Bean(OUTBOUND_CHANNEL)
    public MessageChannel messageChannel(){
        return new DirectChannel();
    }

    @Bean(CHANNEL_HANDLER)
    @ServiceActivator(inputChannel = OUTBOUND_CHANNEL)
    public MessageHandler messageHandler(){
        return mqttPahoMessageHandler("out-1");
    }

    public MqttPahoMessageHandler mqttPahoMessageHandler(String clientId){
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttFactory);
        messageHandler.setAsync(true);
        return messageHandler;
    }

}
