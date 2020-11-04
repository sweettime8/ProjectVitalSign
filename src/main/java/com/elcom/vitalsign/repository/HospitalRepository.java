/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Hospital;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface HospitalRepository extends PagingAndSortingRepository<Hospital, UUID> {

    Page<Hospital> findAllByStatusAndIsDeleted(Integer status, Integer isDeleted, Pageable aInPageable);
}
