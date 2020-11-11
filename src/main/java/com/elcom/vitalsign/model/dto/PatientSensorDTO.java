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
public class PatientSensorDTO {
    
    private String id;
    
    private String fullName;
    
    private String birthDate;

    private String gender;
    
    private List<Sensor> sensorLst;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Sensor> getSensorLst() {
        return sensorLst;
    }

    public void setSensorLst(List<Sensor> sensorLst) {
        this.sensorLst = sensorLst;
    }
    
    
}
