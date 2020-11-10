package com.elcom.vitalsign;

import com.elcom.vitalsign.mqtt.MqttSubscriberInitApp;
import com.elcom.vitalsign.process.DaemonThreadFactory;
import com.elcom.vitalsign.process.MqttConsumerDataBp;
import com.elcom.vitalsign.process.MqttConsumerDataSpo2;
import com.elcom.vitalsign.process.MqttConsumerDataTemp;
import com.elcom.vitalsign.process.MqttConsumerInitApp;
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

        ApplicationContext applicationContext = SpringApplication.run(VitalsignProjectServicesApplication.class, args);
        BlockingQueue sharedQueueData = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDataBp = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDataSpo2 = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDataTemp = new LinkedBlockingQueue();

        Runnable prodThread = new MqttSubscriberInitApp(sharedQueueData,sharedQueueDataBp,sharedQueueDataSpo2,
                sharedQueueDataTemp,applicationContext);
        Runnable consThread = new MqttConsumerInitApp(sharedQueueData, applicationContext);
        
        Runnable consThreadBp = new MqttConsumerDataBp(sharedQueueDataBp, applicationContext);
        Runnable consThreadSpo2 = new MqttConsumerDataSpo2(sharedQueueDataSpo2, applicationContext);
        Runnable consThreadTemp = new MqttConsumerDataTemp(sharedQueueDataTemp, applicationContext);
        
        ExecutorService executorService = Executors.newFixedThreadPool(5, new DaemonThreadFactory());
        executorService.submit(prodThread);
        executorService.submit(consThread);
        executorService.submit(consThreadBp);
        executorService.submit(consThreadSpo2);
        executorService.submit(consThreadTemp);

    }

}
