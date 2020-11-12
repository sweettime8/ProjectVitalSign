/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.mqtt;

import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.model.measuredata.DataBp;
import com.elcom.vitalsign.model.measuredata.DataSpo2;
import com.elcom.vitalsign.model.measuredata.DataTemp;
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
    private final BlockingQueue sharedQueueDspTurnOn;
    private final BlockingQueue sharedQueueGetPatient;
    private final BlockingQueue sharedQueueDspUnLnkGate;
    private final BlockingQueue sharedQueueDspLnkGate;
    private final BlockingQueue sharedQueueGateTurnOn;

    private final BlockingQueue sharedQueueDspSearchGate;
    private final BlockingQueue sharedQueueDspAddSensor;
    private final BlockingQueue sharedQueueDspConnSen;
    private final BlockingQueue sharedQueueDspDisConnSen;

    private final BlockingQueue sharedQueueDataBp;
    private final BlockingQueue sharedQueueDataSpo2;
    private final BlockingQueue sharedQueueDataTemp;
    private final DataService dataService;

    public MqttSubscriberInitApp(BlockingQueue sharedQueueData, BlockingQueue sharedQueueDspTurnOn,
            BlockingQueue sharedQueueGetPatient, BlockingQueue sharedQueueDspUnLnkGate, BlockingQueue sharedQueueDspLnkGate,
            BlockingQueue sharedQueueGateTurnOn, BlockingQueue sharedQueueDspSearchGate, BlockingQueue sharedQueueDspAddSensor,
            BlockingQueue sharedQueueDspConnSen, BlockingQueue sharedQueueDspDisConnSen,
            BlockingQueue sharedQueueDataBp, BlockingQueue sharedQueueDataSpo2, BlockingQueue sharedQueueDataTemp,
            ApplicationContext applicationContext) {
        this.sharedQueue = sharedQueueData;
        this.sharedQueueDspTurnOn = sharedQueueDspTurnOn;
        this.sharedQueueGetPatient = sharedQueueGetPatient;
        this.sharedQueueDspUnLnkGate = sharedQueueDspUnLnkGate;
        this.sharedQueueDspLnkGate = sharedQueueDspLnkGate;
        this.sharedQueueGateTurnOn = sharedQueueGateTurnOn;
        this.sharedQueueDspSearchGate = sharedQueueDspSearchGate;
        this.sharedQueueDspAddSensor = sharedQueueDspAddSensor;
        this.sharedQueueDspConnSen = sharedQueueDspConnSen;
        this.sharedQueueDspDisConnSen = sharedQueueDspDisConnSen;

        this.sharedQueueDataBp = sharedQueueDataBp;
        this.sharedQueueDataSpo2 = sharedQueueDataSpo2;
        this.sharedQueueDataTemp = sharedQueueDataTemp;

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
            LOGGER.info("Message received:\n\t" + new String(message.getPayload()));
            if (topic.contains(Constant.DISPLAY_REQ_GATE_SENSOR)) { //display turn on
                try {
                    sharedQueueDspTurnOn.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.GET_PATIENT_LIST)) { //display get patient List
                try {
                    sharedQueueGetPatient.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.DISPLAY_UNLINK_GATE_REQ)) {
                try {
                    sharedQueueDspUnLnkGate.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.DISPLAY_LINK_GATE_REQ)) {
                try {
                    sharedQueueDspLnkGate.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.GATE_REQ_DISPLAY_SENSOR)) {
                try {
                    sharedQueueGateTurnOn.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.DISPLAY_SERVER_SEARCH_GATE_REQ)) { // display search gate
                try {
                    sharedQueueDspSearchGate.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }

            if (topic.contains(Constant.GATE_SERVER_ADD_SENSOR_REQ)) { // add sensor
                try {
                    sharedQueueDspAddSensor.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.RES_CONNECT_TO_SENSOR)) { // connect sensor
                try {
                    sharedQueueDspConnSen.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.RES_DISCONNECT_TO_SENSOR)) {
                try {
                    sharedQueueDspDisConnSen.put(new String(message.getPayload()));
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.RES_TRANSMIT_DATA_SPO2)) {
                if (message != null && message.getPayload() != null) {
                    try {
                        JSONObject jSONObject2 = new JSONObject(new String(message.getPayload()));
                        DataSpo2 dataSpo2 = new DataSpo2();
                        dataSpo2.setGateId(jSONObject2.getString("gate_id"));
                        dataSpo2.setDisplayId(jSONObject2.getString("display_id"));
                        dataSpo2.setSensorId(jSONObject2.getString("sensor_id"));
                        dataSpo2.setMeasureId(jSONObject2.getString("measure_id"));
                        dataSpo2.setTs(Float.parseFloat(jSONObject2.get("ts").toString()));
                        dataSpo2.setSpo2(Integer.parseInt(jSONObject2.get("spo2").toString()));
                        dataSpo2.setPi(Integer.parseInt(jSONObject2.get("pi").toString()));
                        dataSpo2.setPr(Double.parseDouble(jSONObject2.get("pr").toString()));
                        dataSpo2.setStep(Integer.parseInt(jSONObject2.get("step").toString()));
                        if (dataSpo2 != null) {
                            sharedQueueDataSpo2.put(dataSpo2);
                        }
                    } catch (Exception ex) {
                        LOGGER.error(ex.toString());
                    }
                }
            }
            if (topic.contains(Constant.RES_TRANSMIT_DATA_NIBP)) {
                try {
                    DataBp dataBp = new DataBp();
                    JSONObject jSONObject3 = new JSONObject(new String(message.getPayload()));
                    dataBp.setGateId(jSONObject3.getString("gate_id"));
                    dataBp.setDisplayId(jSONObject3.getString("display_id"));
                    dataBp.setSensorId(jSONObject3.getString("sensor_id"));
                    dataBp.setMeasureId(jSONObject3.getString("measure_id"));
                    dataBp.setTs(Float.parseFloat(jSONObject3.get("ts").toString()));
                    dataBp.setDia(Long.parseLong(jSONObject3.get("dia").toString()));
                    dataBp.setSys(Long.parseLong(jSONObject3.get("sys").toString()));
                    dataBp.setMap(Long.parseLong(jSONObject3.get("map").toString()));
                    dataBp.setPr(Integer.parseInt(jSONObject3.get("pr").toString()));
                    if (dataBp != null) {
                        sharedQueueDataBp.put(dataBp);
                    }
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if (topic.contains(Constant.RES_TRANSMIT_DATA_TEMP)) {
                try {
                    JSONObject jSONObject1 = new JSONObject(new String(message.getPayload()));
                    DataTemp dataTemp = new DataTemp();
                    dataTemp.setGateId(jSONObject1.getString("gate_id"));
                    dataTemp.setDisplayId(jSONObject1.getString("display_id"));
                    dataTemp.setSensorId(jSONObject1.getString("sensor_id"));
                    dataTemp.setMeasureId(jSONObject1.getString("measure_id"));
                    dataTemp.setTs(Float.parseFloat(jSONObject1.get("ts").toString()));
                    dataTemp.setTemp(Float.parseFloat(jSONObject1.get("temp").toString()));
                    if (dataTemp != null) {
                        sharedQueueDataTemp.put(dataTemp);
                    }
                } catch (Exception ex) {
                    LOGGER.error(ex.toString());
                }
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
            String[] topicName = {
                "DISPLAY_REQ_GATE_SENSOR", "DISPLAY_UNLINK_GATE_REQ", "GET_PATIENT_LIST",
                "DISPLAY_LINK_GATE_REQ","RES_CONNECT_TO_SENSOR","RES_DISCONNECT_TO_SENSOR",
                "RES_TRANSMIT_DATA_SPO2", "RES_TRANSMIT_DATA_TEMP", "RES_TRANSMIT_DATA_NIBP"
            };
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
            String[] topicName = { "GATE_REQ_DISPLAY_SENSOR"};
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

    private void subscribersGateDemo() throws MqttException {
        LOGGER.info("[subscribersByGate topic ]");
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(false);
        conOpt.setAutomaticReconnect(true);
        conOpt.setConnectionTimeout(10);
        conOpt.setKeepAliveInterval(1800);

        MqttClient mqttClient;
        String[] topicName = {
            "GATE/SERVER/ADD_SENSOR_REQ/#"
        };
        for (String topic : topicName) {
            try {
                mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
                mqttClient.setCallback(this);
                mqttClient.connect(conOpt);
                mqttClient.subscribe(topic, Constant.MQTT_QOS);
                LOGGER.info("[" + topic + "] subscribe topic [" + topic + "] SUCCESS!");
            } catch (Exception ex) {
                LOGGER.info("[" + topic + "] subscribe topic [" + topic + "] FAILED!, ex: " + ex.toString());
            }
        }

    }

    private void subscribersDisplayDemo() throws MqttException {
        LOGGER.info("[subscribersByDisplay topic ]");
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(false);
        conOpt.setAutomaticReconnect(true);
        conOpt.setConnectionTimeout(10);
        conOpt.setKeepAliveInterval(1800);

        MqttClient mqttClient;
        String[] topicName = {
            "DISPLAY/SERVER/SEARCH_GATE_REQ/#"
        };
        for (String topic : topicName) {
            try {
                mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
                mqttClient.setCallback(this);
                mqttClient.connect(conOpt);
                mqttClient.subscribe(topic, Constant.MQTT_QOS);
                LOGGER.info("[" + topic + "] subscribe topic [" + topic + "] SUCCESS!");
            } catch (Exception ex) {
                LOGGER.info("[" + topic + "] subscribe topic [" + topic + "] FAILED!, ex: " + ex.toString());
            }
        }

    }

    @Override
    public void run() {
        try {
            this.subscribersByDisplay();
            this.subscribersByGate();
            this.subscribersGateDemo();
            this.subscribersDisplayDemo();
        } catch (MqttException ex) {
            LOGGER.error(ex.toString());
        }
    }

}
