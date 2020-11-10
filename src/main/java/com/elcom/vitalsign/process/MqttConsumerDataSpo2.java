/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.process;

import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.model.measuredata.DataSpo2;
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
public class MqttConsumerDataSpo2 implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttConsumerDataSpo2.class);

    private final BlockingQueue sharedQueueDataSpo2;
    private final DataService dataService;

    public MqttConsumerDataSpo2(BlockingQueue sharedQueueDataSpo2, ApplicationContext applicationContext) {
        this.sharedQueueDataSpo2 = sharedQueueDataSpo2;
        this.dataService = applicationContext.getBean(DataService.class);
    }

    @Override
    public void run() {
        try {
            List lst;
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(Constant.REST_TIMES_QUEUE);
                if (sharedQueueDataSpo2.size() > 0) {
                    lst = new ArrayList<>();
                    sharedQueueDataSpo2.drainTo(lst);
                    this.dataService.saveDataSpo2((List<DataSpo2>) lst);

                }
            }
        } catch (InterruptedException ex) {
            LOGGER.error(ex.toString());
        }
    }
}
