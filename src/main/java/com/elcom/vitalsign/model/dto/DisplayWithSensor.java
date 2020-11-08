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
public class DisplayWithSensor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String displayId;
    private List<Sensor> sensorLst;

    public DisplayWithSensor() {
    }

    
    
    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public List<Sensor> getSensorLst() {
        return sensorLst;
    }

    public void setSensorLst(List<Sensor> sensorLst) {
        this.sensorLst = sensorLst;
    }
    
    
}
