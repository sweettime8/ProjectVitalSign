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
public class AccountPatientSenSorDTO {

    private static final long serialVersionUID = 1L;

    private String displayId;
    private String gateId;
    private String patientCode;
    private String lstSensor;

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getLstSensor() {
        return lstSensor;
    }

    public void setLstSensor(String lstSensor) {
        this.lstSensor = lstSensor;
    }



    
    
}
