package com.gyg.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @author by gyg
 * @date 2021/10/11 22:48
 * @description
 */
@Configuration
@IntegrationComponentScan
@EnableIntegration
public class MqttReceiveConfig {

    @Autowired
    private MqttProperties mqttProperties;

    private static final String INPUT_CHANNEL = "mqttInputChannel";

    /**
     * 配置链接属性
     *
     * @return
     */
    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
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
     * <p>发送消息通道</p>
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * <p>配置监听队列 序列化转对象</p>
     */
    @Bean
    public MessageProducer messageProducer() {
        MqttPahoMessageDrivenChannelAdapter channelAdapter =
                new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId(), mqttPahoClientFactory(), "/hello", "/hi");
        channelAdapter.setCompletionTimeout(mqttProperties.getTimeout());
        channelAdapter.setConverter(new DefaultPahoMessageConverter());
        channelAdapter.setQos(0);
        channelAdapter.setOutputChannel(mqttInputChannel());
        return channelAdapter;
    }

    /**
     * <p>获取通道数据</p>
     */
    @Bean
    @ServiceActivator(inputChannel = INPUT_CHANNEL)
    public MessageHandler messageHandler() {
        return message -> {
            message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
            System.out.println("************************" + message.getPayload());
        };
    }


}
