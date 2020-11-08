/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.dto;

import com.elcom.vitalsign.model.Sensor;
import java.util.List;

/**
 *
 * @author admin
 */
public class GateWithSensorDisplay {

    private static final long serialVersionUID = 1L;

    private String gateId;
    private String status;

    private List<Sensor> sensorLst;

    public GateWithSensorDisplay() {
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sensor> getSensorLst() {
        return sensorLst;
    }

    public void setSensorLst(List<Sensor> sensorLst) {
        this.sensorLst = sensorLst;
    }

    @Override
    public String toString() {
        return "GateWithSensorDisplay{" + "gateId:" + gateId + ", status:" + status + ", sensor:" + sensorLst + '}';
    }
    
    
    
}
