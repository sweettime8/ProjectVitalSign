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
import com.elcom.vitalsign.model.Sensor;
import com.elcom.vitalsign.model.measuredata.DataSpo2;
import com.elcom.vitalsign.model.measuredata.DataTemp;
import com.elcom.vitalsign.service.DataService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
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
                                LOGGER.info("[BQDataConsumerInitApp - display_id] = " + s.toString());
                                Display display = this.dataService.findGateByDisplayId(s.toString());
                                String gateId = display.getGateId();
                                LOGGER.info("[BQDataConsumerInitApp - gate_id] = " + gateId);
                                
                                Gate gate = this.dataService.findGateById(gateId);
                                LOGGER.info("[BQDataConsumerInitApp - gate_status] = " + gate.getStatus());
                                
                                List<Sensor> lstSensor =  this.dataService.findAllSensorByGateId(gateId);
                                for(Sensor sen : lstSensor){
                                    LOGGER.info("sensor : " + sen.getId() );
                                }
                                
                                
                            }
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
