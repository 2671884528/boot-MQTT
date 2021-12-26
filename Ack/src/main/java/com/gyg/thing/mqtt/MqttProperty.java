package com.gyg.thing.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author by gyg
 * @date 2021/12/25 22:22
 * @description
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
@ConditionalOnProperty(prefix = "mqtt",name = "host")
public class MqttProperty {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接地址
     */
    private String host;

    /**
     * 客户Id（但客户端采用这个clientId，且配置无意义）
     */
    private String clientId;

    /**
     * 默认连接话题
     */
    private String defaultTopic;

    /**
     * 超时时间
     */
    private int timeout;

    /**
     * 保持连接数
     */
    private int keepalive;

    /**
     * <p>消费等级最低级</p>
     */
    public static final Integer ZERO_QOS = 0;

    /**
     * <p>默认等级1</p>
     */
    public static final Integer DEFAULT_QOS = 1;

    /**
     * <p>不同平台（JVM运行平台）的换行符，用于处理指令占包情况</p>
     */
    public static final String SEPARATOR = System.getProperty("line.separator");

    /**
     * <p>送处理采用共享订阅，只能一个客户端完成，接收采用非共享订阅发
     * （会涉及到异步转同步多服务器JVM内存不共享问题）</p>
     */
    public static final String SHARE_PREFIX = "$queue/";

    public MqttConnectOptions initOptions(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setConnectionTimeout(timeout);
        options.setKeepAliveInterval(keepalive);
        options.setAutomaticReconnect(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setServerURIs(new String[]{host});
        options.setAutomaticReconnect(true);
        options.setMaxInflight(50);
        return options;
    }
}
