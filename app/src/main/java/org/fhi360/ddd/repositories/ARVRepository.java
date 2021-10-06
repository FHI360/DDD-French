package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.ARV;
import org.fhi360.ddd.domain.Appointment;

import java.util.List;

@Dao
public interface ARVRepository {
    @Insert
    void save(ARV arv);

    @Update
    void update(ARV arv);

    @Query("SELECT * from ARV")
    List<ARV> findAll();

    @Query("SELECT * from ARV where patient_id =:patientId")
    ARV findByPatientId1(Long patientId);


    @Query("SELECT count(*) FROM arv where patient_id = :patient_id and regimen1 !=null")
    int count(Long patient_id);

    @Query("SELECT  *  FROM arv WHERE CURRENT_TIME > date_next_refill ORDER BY id ASC")
    List<ARV> getDefaulter();

}
