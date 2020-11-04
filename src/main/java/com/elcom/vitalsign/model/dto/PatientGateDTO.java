/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.dto;

/**
 *
 * @author admin
 */
public interface PatientGateDTO {

    String getGateId();

    String getPatientCode();

    String getPatientName();

    String getBirthDate();

    String getMobile();

    String getDepartmentName();

    String getRoomName();

    String getBedName();

    String getEmployeeName();

    String getAdditionalInfo();
}
