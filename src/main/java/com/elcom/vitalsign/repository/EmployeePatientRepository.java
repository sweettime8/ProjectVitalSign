/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.EmployeePatient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface EmployeePatientRepository extends CrudRepository<EmployeePatient, Long> {
}
