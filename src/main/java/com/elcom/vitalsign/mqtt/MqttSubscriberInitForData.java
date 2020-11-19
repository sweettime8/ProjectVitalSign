/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.mqtt;

import com.elcom.vitalsign.config.PropertiesConfig;
import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.model.measuredata.DataBp;
import com.elcom.vitalsign.model.measuredata.DataSpo2;
import com.elcom.vitalsign.model.measuredata.DataTemp;
import com.elcom.vitalsign.service.DataService;
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
 * @author admin
 */
public class MqttSubscriberInitForData implements MqttCallback, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriberInitForData.class);

    private static final String MQTT_BROKER = Constant.MQTT_BROKER_PROTOCOL + "://" + Constant.MQTT_BROKER_HOST + ":" + Constant.MQTT_BROKER_PORT;

    private final BlockingQueue sharedQueue;
    private final DataService dataService;

    public MqttSubscriberInitForData(BlockingQueue sharedQueueData, ApplicationContext applicationContext) {
        this.sharedQueue = sharedQueueData;

        this.dataService = applicationContext.getBean(DataService.class);
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        LOGGER.info("[messageArrived topic]: " + topic);
        if (message != null && message.getPayload() != null) {
            LOGGER.info("Message received:\n" + new String(message.getPayload()));
            try {
                switch (PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME) {
                    case Constant.RES_TRANSMIT_DATA_TEMP:
                        try {
                            JSONObject jSONObject1 = new JSONObject(new String(message.getPayload()));
                            DataTemp dataTemp = new DataTemp();
                            dataTemp.setGateId(jSONObject1.getString("gate_id"));
                            dataTemp.setDisplayId(jSONObject1.getString("display_id"));
                            dataTemp.setSensorId(jSONObject1.getString("sensor_id"));
                            dataTemp.setMeasureId(jSONObject1.getString("measure_id"));
                            dataTemp.setTs(Float.parseFloat(jSONObject1.get("ts").toString()));
                            dataTemp.setTemp(Float.parseFloat(jSONObject1.get("temp").toString()));

                            sharedQueue.put(dataTemp);

                        } catch (Exception ex) {
                            LOGGER.error(ex.toString());
                        }
                        break;
                    case Constant.RES_TRANSMIT_DATA_SPO2:
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

                            sharedQueue.put(dataSpo2);

                        } catch (Exception ex) {
                            LOGGER.error(ex.toString());
                        }
                        break;
                    case Constant.RES_TRANSMIT_DATA_NIBP:
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

                            sharedQueue.put(dataBp);

                        } catch (Exception ex) {
                            LOGGER.error(ex.toString());
                        }
                        break;
                }
            } catch (Exception ex) {
                LOGGER.error(ex.toString());
            }

        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token
    ) {
        try {
            MqttMessage mqttMessage = token.getMessage();
            String msg = new String(mqttMessage.getPayload());
            LOGGER.info("deliveryComplete, msg = " + msg);
        } catch (MqttException ex) {
            LOGGER.error(ex.toString());
        }
    }

    private void subscribersData() throws MqttException {
        LOGGER.info("[ subscribersDATA topic ]");
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(false);
        conOpt.setAutomaticReconnect(true);
        conOpt.setConnectionTimeout(10);
        conOpt.setKeepAliveInterval(1800);
        MqttClient mqttClient;

        String subId = PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME + "/#";
        String topicName = PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME + "/#";
        try {
            mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId(), new MemoryPersistence());
            mqttClient.setCallback(this);
            mqttClient.connect(conOpt);
            mqttClient.subscribe(topicName, Constant.MQTT_QOS);
            LOGGER.info("[" + MqttClient.generateClientId() + "] subscribe topic [" + topicName + "] SUCCESS!");
        } catch (Exception ex) {
            LOGGER.info("[" + MqttClient.generateClientId() + "] subscribe topic [" + topicName + "] FAILED!, ex: " + ex.toString());
        }

    }

    @Override
    public void run() {
        try {
            this.subscribersData();
        } catch (MqttException ex) {
            LOGGER.error(ex.toString());
        }
    }

}
