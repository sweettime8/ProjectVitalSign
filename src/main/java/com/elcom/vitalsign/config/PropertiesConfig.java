/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class PropertiesConfig {

    public static String MQTT_SUBSCRIBE_CLIENT_ID;
    public static String MQTT_SUBSCRIBE_TOPIC_NAME;
    public static boolean APP_MASTER;

    @Autowired
    public PropertiesConfig(@Value("${mqtt.subscribe.client.id}") String mqttSubscribeClientId,
             @Value("${mqtt.subscribe.topic.name}") String mqttSubscribeTopicName,
             @Value("${app.master}") boolean isAppMaster) {
        PropertiesConfig.MQTT_SUBSCRIBE_CLIENT_ID = mqttSubscribeClientId;
        PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME = mqttSubscribeTopicName;
        PropertiesConfig.APP_MASTER = isAppMaster;
    }
}
