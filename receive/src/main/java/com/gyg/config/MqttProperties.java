package com.gyg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author by gyg
 * @date 2021/10/10 12:42
 * @description mqtt属性配置
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttProperties {
    private String host;
    private String username;
    private String password;
    private Integer timeout;
    private Integer keepalive;
    private String topic;
    private String clientId;
}
