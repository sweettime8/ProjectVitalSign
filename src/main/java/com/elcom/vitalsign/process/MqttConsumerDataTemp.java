/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.process;

import com.elcom.vitalsign.constant.Constant;
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
public class MqttConsumerDataTemp implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttConsumerDataTemp.class);

    private final BlockingQueue sharedQueueDataTemp;
    private final DataService dataService;

    public MqttConsumerDataTemp(BlockingQueue sharedQueueDataTemp, ApplicationContext applicationContext) {
        this.sharedQueueDataTemp = sharedQueueDataTemp;
        this.dataService = applicationContext.getBean(DataService.class);
    }

    @Override
    public void run() {
        try {
            List lst;
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(Constant.REST_TIMES_QUEUE);
                if (sharedQueueDataTemp.size() > 0) {
                    lst = new ArrayList<>();
                    sharedQueueDataTemp.drainTo(lst);
                    this.dataService.saveDataTemp((List<DataTemp>) lst);

                }
            }
        } catch (InterruptedException ex) {
            LOGGER.error(ex.toString());
        }
    }
}

