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

    //display turn On
    public static final String SERVER_RES_GATE_SENSOR = "SERVER_RES_GATE_SENSOR"; // Pub
    public static final String DISPLAY_REQ_GATE_SENSOR = "DISPLAY_REQ_GATE_SENSOR"; // Sub

    //display unlink
    public static final String DISPLAY_UNLINK_GATE_RES = "DISPLAY_UNLINK_GATE_RES"; // Pub
    public static final String DISPLAY_UNLINK_GATE_REQ = "DISPLAY_UNLINK_GATE_REQ"; // Sub

    //display add gate
    public static final String DISPLAY_LINK_GATE_RES = "DISPLAY_LINK_GATE_RES"; // Pub
    public static final String DISPLAY_LINK_GATE_REQ = "DISPLAY_LINK_GATE_REQ"; // Sub

    //display get patient List
    public static final String RES_PATIENT_LIST = "RES_PATIENT_LIST"; // Pub
    public static final String GET_PATIENT_LIST = "GET_PATIENT_LIST"; // Sub

    //title turn on gate
    public static final String SERVER_RES_SENSOR_LIST = "SERVER_RES_SENSOR_LIST"; // Pub
    public static final String GATE_REQ_DISPLAY_SENSOR = "GATE_REQ_DISPLAY_SENSOR"; // Sub

    //title Add sensor         
    public static final String SERVER_DISPLAY_ADD_SENSOR_GATE_RES = "SERVER/DISPLAY/ADD_SENSOR_GATE_RES/"; // Pub
    public static final String GATE_SERVER_ADD_SENSOR_REQ = "GATE/SERVER/ADD_SENSOR_REQ"; // Sub

    //title Dispplay search sensor   
    public static final String SERVER_DISPLAY_SEARCH_GATE_RES = "SERVER/DISPLAY/SEARCH_GATE_RES/"; // Pub
    public static final String DISPLAY_SERVER_SEARCH_GATE_REQ = "DISPLAY/SERVER/SEARCH_GATE_REQ"; // Sub

    //title Dispplay search sensor   
    public static final String SERVER_DISPLAY_GET_GATE_SENSOR_LINKED_RES = "SERVER/DISPLAY/GET_GATE_SENSOR_LINKED_RES/"; // Pub
    public static final String DISPLAY_SERVER_GET_GATE_SENSOR_LINKED_REQ = "DISPLAY/SERVER/GET_GATE_SENSOR_LINKED_REQ"; // Sub

    //title Connect Sensor
    public static final String RES_CONNECT_TO_SENSOR = "RES_CONNECT_TO_SENSOR"; // Sub

    //title DIS Connect Sensor
    public static final String RES_DISCONNECT_TO_SENSOR = "RES_DISCONNECT_TO_SENSOR"; // Sub

    //sync data
    public static final String RES_TRANSMIT_DATA_SPO2 = "RES_TRANSMIT_DATA_SPO2";
    public static final String RES_TRANSMIT_DATA_TEMP = "RES_TRANSMIT_DATA_TEMP";
    public static final String RES_TRANSMIT_DATA_NIBP = "RES_TRANSMIT_DATA_NIBP";

    //=================================================================================
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
