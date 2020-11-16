/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.dto;

import com.elcom.vitalsign.model.Sensor;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author admin
 */
public class GateLinkedWithSenSor {
    private static final long serialVersionUID = 1L;

    private String gateSerial;
    private String name;
    private String model;
    private String manufacture;
    private String firmwareVersion;
    private Timestamp lastUpdatedAt;
    
    private List<Sensor> sensorLst;

    public String getGateSerial() {
        return gateSerial;
    }

    public void setGateSerial(String gateSerial) {
        this.gateSerial = gateSerial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public List<Sensor> getSensorLst() {
        return sensorLst;
    }

    public void setSensorLst(List<Sensor> sensorLst) {
        this.sensorLst = sensorLst;
    }
    
    
}
