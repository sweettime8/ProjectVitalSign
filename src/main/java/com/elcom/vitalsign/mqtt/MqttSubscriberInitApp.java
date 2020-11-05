/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.mqtt;

import com.elcom.vitalsign.config.PropertiesConfig;
import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.service.DataService;
import com.elcom.vitalsign.utils.JSONConverter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
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
        if (message != null && message.getPayload() != null) {
            try {
                if(topic.contains(Constant.DISPLAY_REQ_GATE_SENSOR)){
                    JSONObject jsonObj = new JSONObject(new String(message.getPayload()));
                    String displayId = jsonObj.getString("display_id");
                    sharedQueue.put(displayId);
                }
            } catch (Exception ex) {
                LOGGER.error(ex.toString());
            }
        }
        System.out.println("Message received:\n\t" + new String(message.getPayload()));
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

    private void subscribers() throws MqttException {
        LOGGER.info("[ subscribe topic ]");
        List<String> displayLst = this.dataService.findAllDisplayForSubscribe();
        if (displayLst != null && !displayLst.isEmpty()) {
            MqttConnectOptions conOpt = new MqttConnectOptions();
            conOpt.setCleanSession(false);
            conOpt.setAutomaticReconnect(true);
            conOpt.setConnectionTimeout(10);
            conOpt.setKeepAliveInterval(1800);

            MqttClient mqttClient;
            String topicName = "DISPLAY_REQ_GATE_SENSOR";
            String subId;
            for (String displayId : displayLst) {
                subId = Constant.DISPLAY_REQ_GATE_SENSOR + "_" + displayId;
                topicName = Constant.DISPLAY_REQ_GATE_SENSOR + "_" + displayId;
                PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME = Constant.DISPLAY_REQ_GATE_SENSOR;
                try {
                    mqttClient = new MqttClient(MQTT_BROKER, subId, new MemoryPersistence());
                    mqttClient.setCallback(this);
                    mqttClient.connect(conOpt);
                    mqttClient.subscribe(topicName, Constant.MQTT_QOS);
                    LOGGER.info("[" + subId + "] subscribe topic [" + topicName + "] SUCCESS!");
                } catch (Exception ex) {
                    LOGGER.info("[" + subId + "] subscribe topic [" + topicName + "] FAILED!, ex: " + ex.toString());
                }
            }
            
            
        }
    }

    @Override
    public void run() {
        try {
            this.subscribers();
        } catch (MqttException ex) {
            LOGGER.error(ex.toString());
        }
    }

}
