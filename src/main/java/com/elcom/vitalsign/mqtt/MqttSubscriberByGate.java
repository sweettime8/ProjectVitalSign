/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.mqtt;

import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.utils.StringUtil;
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

/**
 *
 * @author Admin
 */
public class MqttSubscriberByGate implements MqttCallback, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriberByGate.class);

    private static final String MQTT_BROKER = Constant.MQTT_BROKER_PROTOCOL + "://" + Constant.MQTT_BROKER_HOST + ":" + Constant.MQTT_BROKER_PORT;

    private final BlockingQueue sharedQueueDataBp;
    private final BlockingQueue sharedQueueDataSpo2;
    private final BlockingQueue sharedQueueDataTemp;
    private final String gateId;

    public MqttSubscriberByGate(BlockingQueue sharedQueueDataBp, BlockingQueue sharedQueueDataSpo2,
            BlockingQueue sharedQueueDataTemp, String gateId) {
        this.sharedQueueDataBp = sharedQueueDataBp;
        this.sharedQueueDataSpo2 = sharedQueueDataSpo2;
        this.sharedQueueDataTemp = sharedQueueDataTemp;
        this.gateId = gateId;
    }

    @Override
    public void connectionLost(Throwable cause) {
        LOGGER.info("Connection lost because: " + cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        LOGGER.info("Function : messageArrived() ");
        if (message != null && message.getPayload() != null) {

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

    private void subscribers() throws MqttException {
        if (StringUtil.isNullOrEmpty(this.gateId)) {
            return;
        }

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(false);
        conOpt.setAutomaticReconnect(true);
        conOpt.setConnectionTimeout(10);
        conOpt.setKeepAliveInterval(1800);

        MqttClient mqttClient;
        try {
            mqttClient = new MqttClient(MQTT_BROKER, Constant.DISPLAY_REQ_GATE_SENSOR /*+ "_" + this.gateId*/, new MemoryPersistence());
            mqttClient.setCallback(this);
            mqttClient.connect(conOpt);
            mqttClient.subscribe(Constant.DISPLAY_REQ_GATE_SENSOR /*+ "_" + this.gateId*/, Constant.MQTT_QOS);
            LOGGER.info("Subscribe topic [" + Constant.DISPLAY_REQ_GATE_SENSOR /*+ "_" + this.gateId*/ + "] SUCCESS!");
        } catch (Exception ex) {
            LOGGER.info("Subscribe topic [" + Constant.DISPLAY_REQ_GATE_SENSOR /*+ "_" + this.gateId*/ + "] FAILED!, ex: " + ex.toString());
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
