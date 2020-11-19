/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.controller;

import com.elcom.vitalsign.exception.ValidationException;
import com.elcom.vitalsign.model.Patient;
import com.elcom.vitalsign.model.dto.ResponseData;
import com.elcom.vitalsign.service.PatientService;
import com.elcom.vitalsign.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/patient")
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{patientCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> findByPatientCode(@PathVariable("patientCode") String patientCode) {

        if (StringUtil.isNullOrEmpty(patientCode)) {
            throw new ValidationException("patientId không hợp lệ");
        }

        Patient patient = service.findByPatientCode(patientCode);

        return new ResponseEntity<>(
                patient != null ? new ResponseData(HttpStatus.OK.value(), patient)
                                : new ResponseData(HttpStatus.NO_CONTENT.value(), null), HttpStatus.OK);
    }

}
