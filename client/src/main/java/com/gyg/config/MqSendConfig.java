package com.gyg.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author by gyg
 * @date 2021/10/11 21:22
 * @description 消息发送配置
 */
@Configuration
@IntegrationComponentScan
public class MqSendConfig {

    public static final String OUTPUT_CHANNEL = "mqttOutputChannel";

    @Autowired
    MqttProperties mqttProperties;

    /**
     * 配置链接属性
     *
     * @return
     */
    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setConnectionTimeout(mqttProperties.getTimeout());
        mqttConnectOptions.setUserName(mqttProperties.getUsername());
        mqttConnectOptions.setPassword(mqttProperties.getPassword().toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{mqttProperties.getHost()});
        mqttConnectOptions.setKeepAliveInterval(mqttProperties.getKeepalive());
        return mqttConnectOptions;
    }

    /**
     * <p>MQTT 连接工厂</p>
     */
    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(mqttConnectOptions());
        return factory;
    }

    /**
     * <p>消息处理,转序列化</p>
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = OUTPUT_CHANNEL)
    public MessageHandler messageHandler(){
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttProperties.getClientId(),mqttPahoClientFactory());
        // 消息发送/传递事件将被适当配置的“ApplicationListener”或事件入站通道适配器发布以供接收。
        messageHandler.setAsync(true);
        // 默认队列
        messageHandler.setDefaultTopic(mqttProperties.getTopic());
        return messageHandler;
    }

    /**
     * <p>发送消息通道</p>
     */
    @Bean
    public MessageChannel mqttOutboundChannel(){
        return new DirectChannel();
    }
}
