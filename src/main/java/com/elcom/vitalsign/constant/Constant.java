/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.constant;

/**
 *
 * @author admin
 */
public class Constant {

    /* MQTT */
    public static final String MQTT_BROKER_PROTOCOL = "tcp";
    public static final String MQTT_BROKER_HOST = "103.21.151.182";
    public static final int MQTT_BROKER_PORT = 1883;

    public static final int MQTT_QOS = 1;

    public static final String MQTT_TOPIC_DATA_ECG = "VITALSIGN-TOPIC-DATA-ECG";
    public static final String MQTT_SUB_ID_DATA_ECG = "VITALSIGN-SUB-ID-DATA-ECG";

    public static final String MQTT_TOPIC_DATA_BP = "VITALSIGN-TOPIC-DATA-BP";
    public static final String MQTT_SUB_ID_DATA_BP = "VITALSIGN-SUB-ID-DATA-BP";

    public static final String MQTT_TOPIC_DATA_SPO2 = "VITALSIGN-TOPIC-DATA-SPO2";
    public static final String MQTT_SUB_ID_DATA_SPO2 = "VITALSIGN-SUB-ID-DATA-SPO2";

    public static final String MQTT_TOPIC_DATA_SPO2_WAVE = "VITALSIGN-TOPIC-DATA-SPO2-WAVE";
    public static final String MQTT_SUB_ID_DATA_SPO2_WAVE = "VITALSIGN-SUB-ID-DATA-SPO2-WAVE";

//    public static final String MQTT_TOPIC_DATA_RR  = "VITALSIGN-TOPIC-DATA-RR";
    //public static final String MQTT_SUB_ID_DATA_RR = "VITALSIGN-SUB-ID-DATA-RR";
    public static final String MQTT_TOPIC_DATA_HR_RR = "VITALSIGN-TOPIC-DATA-HR-RR";
    public static final String MQTT_SUB_ID_DATA_HR_RR = "VITALSIGN-SUB-ID-DATA-HR-RR";

    public static final String MQTT_TOPIC_DATA_TEMP = "VITALSIGN-TOPIC-DATA-TEMP";
    public static final String MQTT_SUB_ID_DATA_TEMP = "VITALSIGN-SUB-ID-DATA-TEMP";

    public static final int JDBC_BATCH_SIZE = 30;
    public static final int REST_TIMES_QUEUE = 10 * 1000; // Thời gian nghỉ giữa những lần quét vào lấy data MQTT trong queue để insert DB
    public static final int DELETE_DATA_AFTER_DAY = 31; // Xóa data sau 31 ngày
    public static final String ADD_PARTITION_NEXT_DAY = "0 45 23 ? * *"; // Chạy vào 23h45' mỗi ngày

    public static final int SENSOR_MEASURE_STATE_ACTIVE = 1;
    public static final int SENSOR_MEASURE_STATE_INACTIVE = 0;

    public static final int MARKED_INACTIVE_SENSOR_AT = 20; // Sau x giây mà Sensor không hoạt động thì đánh dấu InActive
    public static final int MARKED_INACTIVE_GATE_AT = 20; // Sau x giây mà Gate không hoạt động thì đánh dấu InActive

    public static final int SCHE_INACTIVE_SENSOR = 20000; // Scheduler quét đánh dấu InActive Sensor lặp lại sau x giây
    public static final int SCHE_INACTIVE_GATE = 20000; // Scheduler quét đánh dấu InActive Gate lặp lại sau x giây
}
