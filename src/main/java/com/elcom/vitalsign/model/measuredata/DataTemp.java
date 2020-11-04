/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.measuredata;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.Type;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "data_temp")
public class DataTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", length = 36, updatable = false, nullable = false)
    private UUID id;

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

    @Transient
    private Long measureTime;

    @Column(name = "measure_at")
    private Timestamp measureAt;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    void preInsert() {
        this.measureAt = new Timestamp(this.measureTime);
    }

    public DataTemp() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
     * @return the measureAt
     */
    public Timestamp getMeasureAt() {
        return measureAt;
    }

    /**
     * @param measureAt the measureAt to set
     */
    public void setMeasureAt(Timestamp measureAt) {
        this.measureAt = measureAt;
    }

    /**
     * @return the createdAt
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the measureTime
     */
    public Long getMeasureTime() {
        return measureTime;
    }

    /**
     * @param measureTime the measureTime to set
     */
    public void setMeasureTime(Long measureTime) {
        this.measureTime = measureTime;
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
     * @param temp the temp1 to set
     */
    public void setTemp(Float temp) {
        this.temp = temp;
    }

}
