package com.elcom.vitalsign;

import com.elcom.vitalsign.mqtt.MqttSubscriberInitApp;
import com.elcom.vitalsign.process.BQDataConsumerInitApp;
import com.elcom.vitalsign.process.DaemonThreadFactory;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VitalsignProjectServicesApplication {

    public static void main(String[] args) throws MqttException, URISyntaxException {
        //SpringApplication.run(VitalsignProjectServicesApplication.class, args);
        ApplicationContext applicationContext = SpringApplication.run(VitalsignProjectServicesApplication.class, args);
        BlockingQueue sharedQueueData = new LinkedBlockingQueue();

        Runnable prodThread = new MqttSubscriberInitApp(sharedQueueData, applicationContext);
        Runnable consThread = new BQDataConsumerInitApp(sharedQueueData, applicationContext);
        
        ExecutorService executorService = Executors.newFixedThreadPool(2, new DaemonThreadFactory());
        executorService.submit(prodThread);
        executorService.submit(consThread);

    }

}
