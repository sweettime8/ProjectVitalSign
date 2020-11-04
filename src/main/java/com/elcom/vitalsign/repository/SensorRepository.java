/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface SensorRepository extends PagingAndSortingRepository<Sensor, String> {

    Page<Sensor> findAllByStatusAndIsDeleted(Integer status, Integer isDeleted, Pageable aInPageable);

    Page<Sensor> findAllByStatusAndIsDeletedAndGateId(Integer status, Integer isDeleted, String gateId, Pageable aInPageable);
}
