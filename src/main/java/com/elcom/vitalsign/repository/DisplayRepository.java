/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Display;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface DisplayRepository extends PagingAndSortingRepository<Display, String> {

    @Query(value = " SELECT id FROM display WHERE status = 1 and is_deleted = 0 ", nativeQuery = true)
    List<String> findAllDisplayForSubscribe();
}
