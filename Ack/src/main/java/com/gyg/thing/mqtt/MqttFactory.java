package com.gyg.thing.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.stereotype.Component;

/**
 * @author by gyg
 * @date 2021/12/25 22:43
 * @description
 */
@Component
@ConditionalOnBean(value = MqttProperty.class)
public class MqttFactory extends DefaultMqttPahoClientFactory {

    public MqttFactory(MqttProperty mqttProperty) {
        this.setConnectionOptions(mqttProperty.initOptions());
    }
}
