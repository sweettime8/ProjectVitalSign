/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.service;

import com.elcom.vitalsign.model.Display;
import com.elcom.vitalsign.model.Gate;
import com.elcom.vitalsign.model.Patient;
import com.elcom.vitalsign.model.Sensor;
import com.elcom.vitalsign.model.measuredata.DataBp;
import com.elcom.vitalsign.model.measuredata.DataSpo2;
import com.elcom.vitalsign.model.measuredata.DataTemp;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface DataService {
    
    void saveDataBp(List<DataBp> item);

    void saveDataSpo2(List<DataSpo2> item);

    //void saveDataRr(List<DataRr> item);
    void saveDataTemp(List<DataTemp> item);

    void addDataPartition(String[] tables);

    List<String> findAllDisplayForSubscribe();
    
//    void updateDeviceInActive(String deviceType);

    List<String> findAllGateForSubscribe();
    
    Display findByDisplayId(String id);    
    Display findDisplayBySerialNumber(String id);
    
    Gate findGateById(String id);
    Gate findGateBySerialNumber(String id);
    
    List<Sensor> findAllSensorByGateId(String gateId);
    
    Patient findPatientById(String id);
    
    void unLinkGate(Display display);
    
    void addLinkGate(Display display);
    
    void updateStatusSensor(Sensor sensor);
    
    Display findDisplayByGateId(String gateId);
    
    Sensor findSensorById(String sensorId);
    Sensor findSensorByMac(String sensorMac);
    
    void saveSensor(Sensor sensor);
}
