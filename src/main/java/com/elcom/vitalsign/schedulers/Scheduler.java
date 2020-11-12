/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.schedulers;

import com.elcom.vitalsign.config.PropertiesConfig;
import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class Scheduler {

    private final DataService dataService;

    @Autowired
    public Scheduler(DataService dataService) {
        this.dataService = dataService;
    }


    @Scheduled(cron = Constant.ADD_PARTITION_NEXT_DAY)
    public void addDataPartition() throws InterruptedException {
        if (PropertiesConfig.APP_MASTER) {
            String[] tables = {"data_bp", "data_spo2","data_temp"};
            this.dataService.addDataPartition(tables);
            System.out.println("Add partition success!");
        }
    }

    @Bean
    public TaskScheduler taskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        return scheduler;
    }
}
