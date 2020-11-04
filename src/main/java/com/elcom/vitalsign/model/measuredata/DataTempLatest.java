/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.measuredata;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "data_temp_latest")
public class DataTempLatest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gate_id")
    private String gateId;

    @Column(name = "sensor_id")
    private String sensorId;
    
    @Column(name = "temp")
    private Float temp;
        
    @Column(name = "measure_id")
    private String measureId;
    
    @Column(name = "step_id")
    private String stepId;
    
    @Column(name = "last_measure_at")
    private Timestamp lastMeasureAt;
    
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;

    public DataTempLatest() {
    }
    
    public DataTempLatest(String gateId, String sensorId, Float temp, String measureId, String stepId, Timestamp lastMeasureAt) {
        this.gateId = gateId;
        this.sensorId = sensorId;
        this.temp = temp;
        this.measureId = measureId;
        this.stepId = stepId;
        this.lastMeasureAt = lastMeasureAt;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
     * @return the sensorId
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     * @param sensorId the sensorId to set
     */
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * @return the measureId
     */
    public String getMeasureId() {
        return measureId;
    }

    /**
     * @param measureId the measureId to set
     */
    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    /**
     * @return the lastMeasureAt
     */
    public Timestamp getLastMeasureAt() {
        return lastMeasureAt;
    }

    /**
     * @param lastMeasureAt the lastMeasureAt to set
     */
    public void setLastMeasureAt(Timestamp lastMeasureAt) {
        this.lastMeasureAt = lastMeasureAt;
    }

    /**
     * @return the lastUpdatedAt
     */
    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    /**
     * @param lastUpdatedAt the lastUpdatedAt to set
     */
    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
    
    /**
     * @return the stepId
     */
    public String getStepId() {
        return stepId;
    }

    /**
     * @param stepId the stepId to set
     */
    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    /**
     * @return the temp
     */
    public Float getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(Float temp) {
        this.temp = temp;
    }

}

