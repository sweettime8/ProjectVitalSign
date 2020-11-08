/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.process;

import com.elcom.vitalsign.config.PropertiesConfig;
import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.model.Display;
import com.elcom.vitalsign.model.Gate;
import com.elcom.vitalsign.model.Patient;
import com.elcom.vitalsign.model.Sensor;
import com.elcom.vitalsign.model.dto.DisplayWithSensor;
import com.elcom.vitalsign.model.dto.GateWithSensorDisplay;
import com.elcom.vitalsign.model.measuredata.DataBp;
import com.elcom.vitalsign.model.measuredata.DataSpo2;
import com.elcom.vitalsign.model.measuredata.DataTemp;
import com.elcom.vitalsign.mqtt.MqttPulisherRes;
import com.elcom.vitalsign.service.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author admin
 */
public class MqttConsumerInitApp implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttConsumerInitApp.class);

    private final BlockingQueue sharedQueue;
    private final DataService dataService;

    public MqttConsumerInitApp(BlockingQueue sharedQueue, ApplicationContext applicationContext) {
        this.sharedQueue = sharedQueue;
        this.dataService = applicationContext.getBean(DataService.class);
    }

    @Override
    public void run() {
        try {
            List lst;
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(Constant.REST_TIMES_QUEUE);
                if (sharedQueue.size() > 0) {
                    lst = new ArrayList<>();
                    sharedQueue.drainTo(lst);
                    switch (PropertiesConfig.MQTT_SUBSCRIBE_TOPIC_NAME) {
                        case Constant.DISPLAY_REQ_GATE_SENSOR:
                            for (Object s : lst) {
                                LOGGER.info("Case = [DISPLAY_REQ_GATE_SENSOR] : " + s.toString());
                                try {
                                    JSONObject jSONObject = new JSONObject(s.toString());
                                    Display display = this.dataService.findByDisplayId(jSONObject.getString("display_id"));
                                    String gateId = display.getGateId();

                                    Gate gate = this.dataService.findGateById(gateId);

                                    List<Sensor> lstSensor = this.dataService.findAllSensorByGateId(gateId);

                                    GateWithSensorDisplay gateSensor = new GateWithSensorDisplay();
                                    gateSensor.setGateId(gateId);
                                    gateSensor.setStatus(gate.getStatus() == 1 ? "active" : "inactive");
                                    gateSensor.setSensorLst(lstSensor);

                                    String topic = "SERVER_RES_GATE_SENSOR_" + jSONObject.getString("display_id");

                                    MqttPulisherRes mqtt = new MqttPulisherRes();
                                    mqtt.publisherDisplayTurnOn(topic, gateSensor);
                                } catch (MqttException | JSONException e) {
                                    LOGGER.error(e.toString());
                                }
                            }
                            break;

                        case Constant.DISPLAY_UNLINK_GATE_REQ:
                            for (Object s : lst) {
                                try {
                                    LOGGER.info("Case = [DISPLAY_UNLINK_GATE_REQ] : " + s.toString());
                                    JSONObject jSONObject = new JSONObject(s.toString());

                                    Map<String, String> map = new LinkedHashMap<String, String>();
                                    map.put("display_id", jSONObject.getString("display_id"));
                                    map.put("gate_id", jSONObject.getString("gate_id"));

                                    Display display = this.dataService.findByDisplayId(jSONObject.getString("display_id"));
                                    if (display != null) {
                                        if (jSONObject.getString("gate_id").equals(display.getGateId())) {
                                            this.dataService.unLinkGate(display);
                                            map.put("result", "OK");
                                        } else {
                                            map.put("result", "FAILED");
                                        }
                                    } else {
                                        map.put("result", "FAILED");
                                    }

                                    String topic = "DISPLAY_UNLINK_GATE_RES" + jSONObject.getString("display_id");

                                    MqttPulisherRes mqtt = new MqttPulisherRes();
                                    mqtt.publisherDisplayUnLinkGate(topic, map);

                                } catch (JsonProcessingException | MqttException | JSONException e) {
                                    LOGGER.error(e.toString());
                                }

                            }
                            break;

                        case Constant.DISPLAY_LINK_GATE_REQ:
                            for (Object s : lst) {
                                try {
                                    LOGGER.info("Case = [DISPLAY_LINK_GATE_REQ] : " + s.toString());
                                    JSONObject jSONObject = new JSONObject(s.toString());

                                    Map<String, String> map = new LinkedHashMap<>();
                                    map.put("display_id", jSONObject.getString("display_id"));
                                    map.put("gate_id", jSONObject.getString("gate_id"));

                                    Display display = this.dataService.findByDisplayId(jSONObject.getString("display_id"));
                                    if (display != null) {
                                        if (jSONObject.getString("gate_id").equals(display.getGateId())) {
                                            map.put("result", "LINKED");
                                        } else if (display.getGateId().equals("0")) {
                                            display.setGateId(jSONObject.getString("gate_id"));
                                            this.dataService.addLinkGate(display);
                                            map.put("result", "OK");
                                        }
                                    } else {
                                        map.put("result", "FAILED");
                                    }

                                    String topic = "DISPLAY_LINK_GATE_RES" + jSONObject.getString("display_id");

                                    MqttPulisherRes mqtt = new MqttPulisherRes();
                                    mqtt.publisherDisplayAddLinkGate(topic, map);

                                } catch (JsonProcessingException | MqttException | JSONException e) {
                                    LOGGER.error(e.toString());
                                }

                            }
                            break;

                        case Constant.GET_PATIENT_LIST:

                            for (Object s : lst) {
                                LOGGER.info("Case = [GET_PATIENT_LIST] : " + s.toString());
                                JSONObject jSONObject = new JSONObject(s.toString());

                                Display display = this.dataService.findByDisplayId(jSONObject.getString("display_id"));
                                String gateId = display.getGateId();
                                List<Sensor> lstSensor = this.dataService.findAllSensorByGateId(gateId);

                                List<Patient> lstPatient = new ArrayList<>();
                                for (Sensor sen : lstSensor) {
                                    try {
                                        Patient pa = this.dataService.findPatientById(sen.getPatientId());
                                        lstPatient.add(pa);
                                    } catch (Exception e) {
                                        LOGGER.error(e.toString());
                                    }
                                }
                                String topic = "RES_PATIENT_LIST_" + jSONObject.getString("display_id");

                                MqttPulisherRes mqtt = new MqttPulisherRes();
                                try {
                                    mqtt.publisherListPatient(topic, lstPatient);
                                } catch (MqttException ex) {
                                    java.util.logging.Logger.getLogger(MqttConsumerInitApp.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        case Constant.GATE_REQ_DISPLAY_SENSOR:
                            for (Object s : lst) {
                                LOGGER.info("Case = [GATE_REQ_DISPLAY_SENSOR] : " + s.toString());
                                try {
                                    JSONObject jSONObject = new JSONObject(s.toString());
                                    String gateId = jSONObject.getString("gateId");
                                    Gate gate = this.dataService.findGateById(gateId);

                                    List<Sensor> lstSensor = this.dataService.findAllSensorByGateId(gateId);
                                    
                                    Display display = this.dataService.findDisplayByGateId(gateId);

                                    DisplayWithSensor displayWithSensor = new DisplayWithSensor();
                                    displayWithSensor.setDisplayId(display.getId());
                                    displayWithSensor.setSensorLst(lstSensor);

                                    String topic = "SERVER_RES_SENSOR_LIST_" + jSONObject.getString("gateId");

                                    MqttPulisherRes mqtt = new MqttPulisherRes();
                                    mqtt.publisherGateTurnOn(topic, displayWithSensor);
                                } catch (Exception e) {
                                    LOGGER.error(e.toString());
                                }
                            }
                            break;

                        case Constant.MQTT_TOPIC_DATA_BP:
                            this.dataService.saveDataBp((List<DataBp>) lst);
                            break;
                        case Constant.MQTT_TOPIC_DATA_SPO2:
                            this.dataService.saveDataSpo2((List<DataSpo2>) lst);
                            break;
                        case Constant.MQTT_TOPIC_DATA_TEMP:
                            this.dataService.saveDataTemp((List<DataTemp>) lst);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (InterruptedException ex) {
            LOGGER.error(ex.toString());
        }
    }
}
