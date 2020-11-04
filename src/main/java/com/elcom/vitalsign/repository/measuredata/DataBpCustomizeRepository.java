/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository.measuredata;

import com.elcom.vitalsign.constant.Constant;
import com.elcom.vitalsign.model.measuredata.DataBp;
import com.elcom.vitalsign.model.measuredata.DataBpLatest;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public class DataBpCustomizeRepository {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBpCustomizeRepository.class);
    
    private SessionFactory sessionFactory;
    
    @Autowired
    public DataBpCustomizeRepository(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null)
            throw new NullPointerException("factory is not a hibernate factory");
        
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }
    
    public void progressDataLatest(DataBp item, String gateIdLst) {
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();
            try {
                Query query = session.createNativeQuery(" SELECT id FROM data_bp_latest WHERE gate_id = ? AND sensor_id = ? ");
                query.setParameter(1, item.getGateId());
                query.setParameter(2, item.getSensorId());
                Object result = query.getSingleResult();

                // Không throws NoResultException thì là tìm thấy, cần update
                Long id = ((Integer) result).longValue();
                query = session.createNativeQuery(" UPDATE data_bp_latest SET sys_v = ?, dia_v = ? "
                                        + ", map_v = ?, pr = ?, measure_id = ?, step_id = ?, last_measure_at = ? WHERE id = ? ");
                query.setParameter(1, item.getSysValue());
                query.setParameter(2, item.getDiaValue());
                query.setParameter(3, item.getMapValue());
                query.setParameter(4, item.getPr());
                query.setParameter(5, item.getMeasureId());
                query.setParameter(6, item.getStepId());
                query.setParameter(7, item.getMeasureAt());
                query.setParameter(8, id);
                query.executeUpdate();
                
                //Đánh dấu gate đang hoạt động
                query = session.createNativeQuery(" UPDATE gate SET activity_state = ?, activity_last_time = current_timestamp() WHERE status = 1 AND activity_state = ? AND id IN ("+gateIdLst+") ");
                query.setParameter(1, Constant.SENSOR_MEASURE_STATE_ACTIVE);
                query.setParameter(2, Constant.SENSOR_MEASURE_STATE_INACTIVE);
                query.executeUpdate();
                
                //Đánh dấu sensor đang hoạt động
//                query = session.createNativeQuery(" UPDATE sensor SET measure_state = ?, measure_last_time = current_timestamp() WHERE status = 1 AND measure_state = ? AND id = ? ");
//                query.setParameter(1, Constant.SENSOR_MEASURE_STATE_ACTIVE);
//                query.setParameter(2, Constant.SENSOR_MEASURE_STATE_INACTIVE);
//                query.setParameter(3, item.getSensorId());
//                query.executeUpdate();
            }catch(NoResultException ex) {
                //Ko tìm thấy thì insert mới
                LOGGER.error(ex.toString());
                session.save(new DataBpLatest(item.getGateId(), item.getSensorId(), item.getSysValue(), item.getDiaValue()
                                , item.getMapValue(), item.getPr(), item.getMeasureId(), item.getStepId(), item.getMeasureAt()));
            }
            tx.commit();
        }catch(Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
    }
    
    private Session openSession() {
        return this.sessionFactory.openSession();
    }
    
    private void closeSession(Session session) {
        if (session != null)
            session.close();
    }
}

