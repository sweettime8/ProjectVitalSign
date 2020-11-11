/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.process;

import com.elcom.vitalsign.model.Display;
import com.elcom.vitalsign.model.Gate;
import com.elcom.vitalsign.model.Patient;
import com.elcom.vitalsign.model.Sensor;
import com.elcom.vitalsign.model.dto.DisplayWithSensor;
import com.elcom.vitalsign.model.dto.GateWithSensorDisplay;
import com.elcom.vitalsign.mqtt.MqttPulisherRes;
import com.elcom.vitalsign.service.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
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

    private final BlockingQueue sharedQueueDspTurnOn;
    private final BlockingQueue sharedQueueGetPatient;
    private final BlockingQueue sharedQueueDspUnLnkGate;
    private final BlockingQueue sharedQueueDspLnkGate;
    private final BlockingQueue sharedQueueGateTurnOn;
    private final BlockingQueue sharedQueueDspConnSen;
    private final BlockingQueue sharedQueueDspDisConnSen;

    private final DataService dataService;

    public MqttConsumerInitApp(BlockingQueue sharedQueueDspTurnOn, BlockingQueue sharedQueueGetPatient,
            BlockingQueue sharedQueueDspUnLnkGate, BlockingQueue sharedQueueDspLnkGate, BlockingQueue sharedQueueGateTurnOn,
            BlockingQueue sharedQueueDspConnSen, BlockingQueue sharedQueueDspDisConnSen,
            ApplicationContext applicationContext) {
        this.sharedQueueDspTurnOn = sharedQueueDspTurnOn;
        this.sharedQueueGetPatient = sharedQueueGetPatient;
        this.sharedQueueDspUnLnkGate = sharedQueueDspUnLnkGate;
        this.sharedQueueDspLnkGate = sharedQueueDspLnkGate;
        this.sharedQueueGateTurnOn = sharedQueueGateTurnOn;
        this.sharedQueueDspConnSen = sharedQueueDspConnSen;
        this.sharedQueueDspDisConnSen = sharedQueueDspDisConnSen;

        this.dataService = applicationContext.getBean(DataService.class);
    }

    @Override
    public void run() {
        try {
            List lstDspTurnOn;
            List lstPatients;
            List lstDspUnLnkGate;
            List lstDspLnkGate;
            List lstGateTurnOn;
            List lstDspConnSen;
            List lstDspDisConnSen;

            while (!Thread.currentThread().isInterrupted()) {
                if (sharedQueueDspTurnOn.size() > 0) {
                    lstDspTurnOn = new ArrayList<>();
                    sharedQueueDspTurnOn.drainTo(lstDspTurnOn);
                    for (Object s : lstDspTurnOn) {
                        LOGGER.info("Consummer - [DISPLAY_REQ_GATE_SENSOR] : " + s.toString());
                        try {
                            JSONObject jSONObject = new JSONObject(s.toString());
                            Display display = this.dataService.findByDisplayId(jSONObject.getString("display_id"));
                            String gateId = display.getGateId();

                            Gate gate = this.dataService.findGateById(gateId);
                            if (gate == null) {
                                LOGGER.info("Gate is not linked for Display");
                                break;
                            }
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
                }
                //---------------------------------------

                if (sharedQueueGetPatient.size() > 0) {
                    lstPatients = new ArrayList<>();
                    sharedQueueGetPatient.drainTo(lstPatients);
                    for (Object s : lstPatients) {
                        try {
                            LOGGER.info("Consummer - [GET_PATIENT_LIST] : " + s.toString());
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
                            mqtt.publisherListPatient(topic, lstPatient);

                        } catch (Exception e) {
                            LOGGER.error(e.toString());
                        }
                    }
                }

                //----------------------------
                if (sharedQueueDspUnLnkGate.size() > 0) {
                    lstDspUnLnkGate = new ArrayList<>();
                    sharedQueueDspUnLnkGate.drainTo(lstDspUnLnkGate);
                    for (Object s : lstDspUnLnkGate) {
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
                            String topic = "DISPLAY_UNLINK_GATE_RES_" + jSONObject.getString("display_id");

                            MqttPulisherRes mqtt = new MqttPulisherRes();
                            mqtt.publisherDisplayUnLinkGate(topic, map);

                        } catch (JsonProcessingException | MqttException | JSONException e) {
                            LOGGER.error(e.toString());
                        }

                    }
                }

                //------------------------------------------
                if (sharedQueueDspLnkGate.size() > 0) {
                    lstDspLnkGate = new ArrayList<>();
                    sharedQueueDspLnkGate.drainTo(lstDspLnkGate);
                    for (Object s : lstDspLnkGate) {
                        try {
                            LOGGER.info("Consummer - [DISPLAY_LINK_GATE_REQ] : " + s.toString());
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

                            String topic = "DISPLAY_LINK_GATE_RES_" + jSONObject.getString("display_id");

                            MqttPulisherRes mqtt = new MqttPulisherRes();
                            mqtt.publisherDisplayAddLinkGate(topic, map);

                        } catch (JsonProcessingException | MqttException | JSONException e) {
                            LOGGER.error(e.toString());
                        }

                    }
                }

                //--------------------------------------------------
                if (sharedQueueGateTurnOn.size() > 0) {
                    lstGateTurnOn = new ArrayList<>();
                    sharedQueueGateTurnOn.drainTo(lstGateTurnOn);
                    for (Object s : lstGateTurnOn) {
                        LOGGER.info("Consummer - [GATE_REQ_DISPLAY_SENSOR] : " + s.toString());
                        try {
                            JSONObject jSONObject = new JSONObject(s.toString());
                            String gateId = jSONObject.getString("gate_id");
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
                }

                //------------------------------------------------------
                if (sharedQueueDspDisConnSen.size() > 0) {
                    lstDspDisConnSen = new ArrayList<>();
                    sharedQueueDspDisConnSen.drainTo(lstDspDisConnSen);
                    for (Object s : lstDspDisConnSen) {
                        LOGGER.info("Consummer - [RES_DISCONNECT_TO_SENSOR] : " + s.toString());
                        try {
                            JSONObject jSONObject = new JSONObject(s.toString());
                            String sensorId = jSONObject.getString("sensor_id");
                            String gateId = jSONObject.getString("gate_id");
                            Sensor sensor = this.dataService.findSensorById(sensorId);
                            if (sensor != null) {
                                if (sensor.getGateId().equals(gateId)) {
                                    sensor.setStatus(0);
                                    this.dataService.updateStatusSensor(sensor);
                                }
                            } else {
                                LOGGER.info("Sensor not found");
                            }
                        } catch (Exception e) {
                            LOGGER.error(e.toString());
                        }
                    }
                }
                //------------------------------------------------------------------
                if (sharedQueueDspConnSen.size() > 0) {
                    lstDspConnSen = new ArrayList<>();
                    sharedQueueDspConnSen.drainTo(lstDspConnSen);
                    for (Object s : lstDspConnSen) {
                        LOGGER.info("Consummer - [RES_CONNECT_TO_SENSOR] : " + s.toString());
                        try {
                            JSONObject jSONObject = new JSONObject(s.toString());
                            String sensorId = jSONObject.getString("sensor_id");
                            String gateId = jSONObject.getString("gate_id");
                            Sensor sensor = this.dataService.findSensorById(sensorId);
                            if (sensor != null) {
                                if (sensor.getGateId().equals(gateId)) {
                                    sensor.setStatus(1);
                                    this.dataService.updateStatusSensor(sensor);
                                }
                            }
                        } catch (Exception e) {
                            LOGGER.error(e.toString());
                        }
                    }
                }

            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
    }
}
