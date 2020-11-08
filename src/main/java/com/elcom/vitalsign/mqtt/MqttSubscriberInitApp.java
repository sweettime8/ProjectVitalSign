/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.mqtt;

import com.elcom.vitalsign.config.PropertiesConfig;
import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.service.DataService;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Admin
 */
public class MqttSubscriberInitApp implements MqttCallback, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriberInitApp.class);

    private static final String MQTT_BROKER = Constant.MQTT_BROKER_PROTOCOL + "://" + Constant.MQTT_BROKER_HOST + ":" + Constant.MQTT_BROKER_PORT;

    private final BlockingQueue sharedQueue;
    private final DataService dataService;

    public MqttSubscriberInitApp(BlockingQueue sharedQueueData, ApplicationContext applicationContext) {
        this.sharedQueue = sharedQueueData;
        this.dataService = applicationContext.getBean(DataService.class);
    }

    @Override
    public void connectionLost(Throwable cause) {
        LOGGER.info("Connection lost because: " + cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        LOGGER.info("[messageArrived topic]: " + topic);
        if (topic.contains(Constant.DISPLAY_REQ_GATE_SENSOR)) {
            PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME = Constant.DISPLAY_REQ_GATE_SENSOR;
        }
        if (topic.contains(Constant.GET_PATIENT_LIST)) {
            PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME = Constant.GET_PATIENT_LIST;
        }
        if (topic.contains(Constant.DISPLAY_UNLINK_GATE_REQ)) {
            PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME = Constant.DISPLAY_UNLINK_GATE_REQ;
        }
        if (topic.contains(Constant.DISPLAY_LINK_GATE_REQ)) {
            PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME = Constant.DISPLAY_LINK_GATE_REQ;
        }
        if (topic.contains(Constant.GATE_REQ_DISPLAY_SENSOR)) {
            PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME = Constant.GATE_REQ_DISPLAY_SENSOR;
        }
        LOGGER.info("[PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME] = " + PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME);
        if (message != null && message.getPayload() != null) {
            try {
                sharedQueue.put(new String(message.getPayload()));
                LOGGER.info("Message received:\n\t" + new String(message.getPayload()));
            } catch (Exception ex) {
                LOGGER.error(ex.toString());
            }
        }
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            MqttMessage mqttMessage = token.getMessage();
            String msg = new String(mqttMessage.getPayload());
            LOGGER.info("deliveryComplete, msg = " + msg);
        } catch (MqttException ex) {
            LOGGER.error(ex.toString());
        }
    }

    private void subscribersByDisplay() throws MqttException {
        LOGGER.info("[ subscribersByDisplay topic ]");

        List<String> displayLst = this.dataService.findAllDisplayForSubscribe();
        if (displayLst != null && !displayLst.isEmpty()) {
            MqttConnectOptions conOpt = new MqttConnectOptions();
            conOpt.setCleanSession(false);
            conOpt.setAutomaticReconnect(true);
            conOpt.setConnectionTimeout(10);
            conOpt.setKeepAliveInterval(1800);
            MqttClient mqttClient;
            String[] topicName = {"DISPLAY_REQ_GATE_SENSOR", "DISPLAY_UNLINK_GATE_REQ", "GET_PATIENT_LIST", "DISPLAY_LINK_GATE_REQ"};
            String subId;
            for (String displayId : displayLst) {
                for (String topic : topicName) {
                    subId = topic + "_" + displayId;
                    topic = topic + "_" + displayId;
                    try {
                        mqttClient = new MqttClient(MQTT_BROKER, subId, new MemoryPersistence());
                        mqttClient.setCallback(this);
                        mqttClient.connect(conOpt);
                        mqttClient.subscribe(topic, Constant.MQTT_QOS);
                        LOGGER.info("[" + subId + "] subscribe topic [" + topic + "] SUCCESS!");
                    } catch (Exception ex) {
                        LOGGER.info("[" + subId + "] subscribe topic [" + topic + "] FAILED!, ex: " + ex.toString());
                    }
                }
            }

        }

    }

    private void subscribersByGate() throws MqttException {
        LOGGER.info("[subscribersByGate topic ]");
        List<String> gateLst = this.dataService.findAllGateForSubscribe();
        if (gateLst != null && !gateLst.isEmpty()) {
            MqttConnectOptions conOpt = new MqttConnectOptions();
            conOpt.setCleanSession(false);
            conOpt.setAutomaticReconnect(true);
            conOpt.setConnectionTimeout(10);
            conOpt.setKeepAliveInterval(1800);

            MqttClient mqttClient;
            String[] topicName = {"GATE_REQ_DISPLAY_SENSOR",
                "MQTT_TOPIC_DATA_BP", "MQTT_TOPIC_DATA_SPO2", "MQTT_TOPIC_DATA_TEMP"};
            String subId;
            for (String gateId : gateLst) {
                for (String topic : topicName) {
                    subId = topic + "_" + gateId;
                    topic = topic + "_" + gateId;
                    try {
                        mqttClient = new MqttClient(MQTT_BROKER, subId, new MemoryPersistence());
                        mqttClient.setCallback(this);
                        mqttClient.connect(conOpt);
                        mqttClient.subscribe(topic, Constant.MQTT_QOS);
                        LOGGER.info("[" + subId + "] subscribe topic [" + topic + "] SUCCESS!");
                    } catch (Exception ex) {
                        LOGGER.info("[" + subId + "] subscribe topic [" + topic + "] FAILED!, ex: " + ex.toString());
                    }
                }
            }
        }

    }

    @Override
    public void run() {
        try {
            this.subscribersByDisplay();
            this.subscribersByGate();
        } catch (MqttException ex) {
            LOGGER.error(ex.toString());
        }
    }

}
