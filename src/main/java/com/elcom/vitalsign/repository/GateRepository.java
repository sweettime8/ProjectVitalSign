/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Gate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface GateRepository extends PagingAndSortingRepository<Gate, String> {

    //@Query(value = " SELECT id FROM gate WHERE status = 1 and is_deleted = 0 ", nativeQuery = true)
    @Query(value = " SELECT serial_number FROM gate WHERE status = 1 and is_deleted = 0 ", nativeQuery = true)
    List<String> findAllGateForSubscribe();
}
