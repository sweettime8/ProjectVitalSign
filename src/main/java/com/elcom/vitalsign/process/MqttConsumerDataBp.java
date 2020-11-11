/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.process;

import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.model.measuredata.DataBp;
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
public class MqttConsumerDataBp implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttConsumerDataBp.class);

    private final BlockingQueue sharedQueueDataBp;
    private final DataService dataService;

    public MqttConsumerDataBp(BlockingQueue sharedQueueDataBp, ApplicationContext applicationContext) {
        this.sharedQueueDataBp = sharedQueueDataBp;
        this.dataService = applicationContext.getBean(DataService.class);
    }

    @Override
    public void run() {
        try {
            List lst;
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(Constant.REST_TIMES_QUEUE);
                if (sharedQueueDataBp.size() > 0) {
                    lst = new ArrayList<>();
                    sharedQueueDataBp.drainTo(lst);
                    this.dataService.saveDataBp((List<DataBp>) lst);

                }
            }
        } catch (InterruptedException ex) {
            LOGGER.error(ex.toString());
        }
    }
}
