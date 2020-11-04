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
@Table(name = "data_bp_latest")
public class DataBpLatest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gate_id")
    private String gateId;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "sys_v")
    private Long sysValue;

    @Column(name = "dia_v")
    private Long diaValue;

    @Column(name = "map_v")
    private Long mapValue;

    @Column(name = "pr")
    private Integer pr;

    @Column(name = "measure_id")
    private String measureId;

    @Column(name = "step_id")
    private String stepId;

    @Column(name = "last_measure_at")
    private Timestamp lastMeasureAt;

    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;

    public DataBpLatest() {
    }

    public DataBpLatest(String gateId, String sensorId, Long sysValue, Long diaValue, Long mapValue,
             Integer pr, String measureId, String stepId, Timestamp lastMeasureAt) {
        this.gateId = gateId;
        this.sensorId = sensorId;
        this.sysValue = sysValue;
        this.diaValue = diaValue;
        this.mapValue = mapValue;
        this.pr = pr;
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
     * @return the sysValue
     */
    public Long getSysValue() {
        return sysValue;
    }

    /**
     * @param sysValue the sysValue to set
     */
    public void setSysValue(Long sysValue) {
        this.sysValue = sysValue;
    }

    /**
     * @return the diaValue
     */
    public Long getDiaValue() {
        return diaValue;
    }

    /**
     * @param diaValue the diaValue to set
     */
    public void setDiaValue(Long diaValue) {
        this.diaValue = diaValue;
    }

    /**
     * @return the mapValue
     */
    public Long getMapValue() {
        return mapValue;
    }

    /**
     * @param mapValue the mapValue to set
     */
    public void setMapValue(Long mapValue) {
        this.mapValue = mapValue;
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
     * @return the pr
     */
    public Integer getPr() {
        return pr;
    }

    /**
     * @param pr the pr to set
     */
    public void setPr(Integer pr) {
        this.pr = pr;
    }
}
