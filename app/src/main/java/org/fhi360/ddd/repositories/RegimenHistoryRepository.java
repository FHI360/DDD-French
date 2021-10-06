package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.fhi360.ddd.domain.RegimenHistory;

import java.util.List;

@Dao
public interface RegimenHistoryRepository {

    @Insert
    void save(RegimenHistory regimenHistory);

    @Query("SELECT * FROM regimenhistory where patient_id =:patientId ")
    List<RegimenHistory> findByPatientId(Long patientId);

    @Query("SELECT * FROM regimenhistory where date_visit =:dateVisit ")
    List<RegimenHistory> findByDateVisit(String dateVisit);

}
