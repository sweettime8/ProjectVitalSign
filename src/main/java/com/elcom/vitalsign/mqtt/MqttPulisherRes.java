/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.mqtt;

import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.model.Patient;
import com.elcom.vitalsign.model.dto.DisplayWithSensor;
import com.elcom.vitalsign.model.dto.GateWithSensorDisplay;
import com.elcom.vitalsign.utils.JSONConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author admin
 */
public class MqttPulisherRes {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttPulisherRes.class);
    private static final String MQTT_BROKER = Constant.MQTT_BROKER_PROTOCOL + "://" + Constant.MQTT_BROKER_HOST + ":" + Constant.MQTT_BROKER_PORT;

    private MqttClient client;
    private MqttMessage message;

    public MqttPulisherRes() throws MqttException {
        if (client == null) {
            client = new MqttClient(MQTT_BROKER, String.valueOf(System.nanoTime()));
            client.connect();
        }
        if (message == null) {
            message = new MqttMessage();
        }
    }

    public void publisherDisplayTurnOn(String topic, GateWithSensorDisplay gds) throws MqttException {

        String data = JSONConverter.toJSON(gds);
        message.setPayload(data.getBytes());
        client.publish(topic, message);

        LOGGER.info("Send Message to Gate ToPic  : " + topic + " \n" + data);

    }

    public void publisherListPatient(String topic, List<Patient> lstPatient) throws MqttException {
        String data = JSONConverter.toJSON(lstPatient);
        LOGGER.info("SendToTopic : " + topic);
        message.setPayload(data.getBytes());
        client.publish(topic, message);

        LOGGER.info("Send Message to Gate ToPic  : " + topic + " \n" + data);
    }

    public void publisherDisplayUnLinkGate(String topic, Map<String, String> mapdata) throws MqttException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String data = objectMapper.writeValueAsString(mapdata);
            message.setPayload(data.getBytes());
            client.publish(topic, message);

            LOGGER.info("Send Message to Gate ToPic  : " + topic + " \n" + data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void publisherDisplayAddLinkGate(String topic, Map<String, String> mapdata) throws MqttException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String data = objectMapper.writeValueAsString(mapdata);
            message.setPayload(data.getBytes());
            client.publish(topic, message);

            LOGGER.info("Send Message to Gate ToPic  : " + topic + " \n" + data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void publisherGateTurnOn(String topic, DisplayWithSensor ds) throws MqttException {
        String data = JSONConverter.toJSON(ds);

        message.setPayload(data.getBytes());
        client.publish(topic, message);
        LOGGER.info("Send Message to Gate ToPic  : " + topic + " \n" + data);

    }

}
