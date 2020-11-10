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

    @Column(name = "display_id")
    private String displayId;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "measure_id")
    private String measureId;

    @Column(name = "ts")
    private Float ts;

    @Column(name = "temp")
    private Float temp;

    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;

    public DataTempLatest() {
    }

    public DataTempLatest(String gateId, String displayId, String sensorId, String measureId, Float ts, Float temp) {
        this.gateId = gateId;
        this.displayId = displayId;
        this.sensorId = sensorId;
        this.measureId = measureId;
        this.ts = ts;
        this.temp = temp;
    }

    @PrePersist
    void preInsert() {
        if (this.lastUpdatedAt == null) {
            this.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }
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

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public Float getTs() {
        return ts;
    }

    public void setTs(Float ts) {
        this.ts = ts;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

}
