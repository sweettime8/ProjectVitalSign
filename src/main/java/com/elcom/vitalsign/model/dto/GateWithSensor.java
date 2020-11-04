/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.dto;

import com.elcom.vitalsign.model.Sensor;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author admin
 */
public class GateWithSensor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String gateId;
    private List<Sensor> sensorLst;

    public GateWithSensor() {
    }

    /**
     * @return the gateId
     */
    public String getGateId() {
        return gateId;
    }

    /**
     * @param gateId the gateId to set
     */
    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    /**
     * @return the sensorLst
     */
    public List<Sensor> getSensorLst() {
        return sensorLst;
    }

    /**
     * @param sensorLst the sensorLst to set
     */
    public void setSensorLst(List<Sensor> sensorLst) {
        this.sensorLst = sensorLst;
    }
}
