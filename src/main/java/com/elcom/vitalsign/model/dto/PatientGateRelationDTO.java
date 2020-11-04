/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.dto;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class PatientGateRelationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String gateId;
    private String patientCode;
    private boolean override;

    public PatientGateRelationDTO() {
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
     * @return the patientCode
     */
    public String getPatientCode() {
        return patientCode;
    }

    /**
     * @param patientCode the patientCode to set
     */
    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    /**
     * @return the override
     */
    public boolean isOverride() {
        return override;
    }

    /**
     * @param override the override to set
     */
    public void setOverride(boolean override) {
        this.override = override;
    }
}
