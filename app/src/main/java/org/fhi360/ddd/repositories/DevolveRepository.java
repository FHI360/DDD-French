package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Devolve;

import java.util.Date;
import java.util.List;

@Dao
public interface DevolveRepository {
    @Query("SELECT * FROM devolve")
    List<Devolve> findByAll();

    @Query("SELECT * FROM devolve where facility_id =:facilityId and patient_id=:patientId")
    Devolve findByOne(int facilityId, int patientId);


    @Insert
    void save(Devolve devolve);

    @Query("UPDATE devolve set date_discontinued =:dateDiscontinued , reason_discontinued =:reasonDiscontinued where  patient_id=:patientId")
    void update1(Long patientId, String dateDiscontinued, String reasonDiscontinued);

    @Update
    void update(Devolve devolve);


    @Query("SELECT * FROM devolve where facility_id =:facilityId and patient_id=:patientId")
    Devolve findByPatient(Long facilityId, Long patientId);
}
