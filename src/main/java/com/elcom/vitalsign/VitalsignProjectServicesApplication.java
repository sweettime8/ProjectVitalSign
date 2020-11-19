package com.elcom.vitalsign;

import com.elcom.vitalsign.config.PropertiesConfig;
import com.elcom.vitalsign.mqtt.MqttSubscriberInitApp;
import com.elcom.vitalsign.mqtt.MqttSubscriberInitForData;
import com.elcom.vitalsign.process.DaemonThreadFactory;
import com.elcom.vitalsign.process.MqttConsumerInitApp;
import com.elcom.vitalsign.process.MqttConsumerInitForData;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VitalsignProjectServicesApplication {

    public static void main(String[] args) throws MqttException, URISyntaxException {
        System.out.println("[CPU] : " + Runtime.getRuntime().availableProcessors());
        ApplicationContext applicationContext = SpringApplication.run(VitalsignProjectServicesApplication.class, args);

        BlockingQueue sharedQueueData = new LinkedBlockingQueue();

        //communice display and gate
        BlockingQueue sharedQueueDspTurnOn = new LinkedBlockingQueue();
        BlockingQueue sharedQueueGetPatient = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDspUnLnkGate = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDspLnkGate = new LinkedBlockingQueue();
        BlockingQueue sharedQueueGateTurnOn = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDspSearchGate = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDspGetGateSensor = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDspAddSensor = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDspConnSen = new LinkedBlockingQueue();
        BlockingQueue sharedQueueDspDisConnSen = new LinkedBlockingQueue();

        Runnable prodThread = null;
        Runnable consThread = null;
        if (PropertiesConfig.APP_MASTER) {
            //subcri init
            prodThread = new MqttSubscriberInitApp(
                    sharedQueueDspTurnOn, sharedQueueGetPatient, sharedQueueDspUnLnkGate, sharedQueueDspLnkGate,
                    sharedQueueGateTurnOn, sharedQueueDspSearchGate, sharedQueueDspGetGateSensor,
                    sharedQueueDspAddSensor, sharedQueueDspConnSen, sharedQueueDspDisConnSen,
                    applicationContext);

            //consumer init
            consThread = new MqttConsumerInitApp(sharedQueueDspTurnOn, sharedQueueGetPatient, sharedQueueDspUnLnkGate,
                    sharedQueueDspLnkGate, sharedQueueGateTurnOn, sharedQueueDspSearchGate, sharedQueueDspGetGateSensor,
                    sharedQueueDspAddSensor, sharedQueueDspConnSen, sharedQueueDspDisConnSen,
                    applicationContext);

        }
        
        //run when appmaster = false
        Runnable prodThreadData = null;
        Runnable consThreadData = null;
        if (!PropertiesConfig.APP_MASTER) {
            prodThreadData = new MqttSubscriberInitForData(sharedQueueData, applicationContext);
            consThreadData = new MqttConsumerInitForData(sharedQueueData, applicationContext);
        }

        //executor 
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new DaemonThreadFactory());

        if (PropertiesConfig.APP_MASTER) {
            executorService.submit(prodThread);
            executorService.submit(consThread);
        }

        //data Spo2, NIBP, TEMP
        if (!PropertiesConfig.APP_MASTER) {
            executorService.submit(prodThreadData);
            executorService.submit(consThreadData);
        }
    }

}
