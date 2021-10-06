package org.fhi360.ddd.repositories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Devolve;
import org.fhi360.ddd.domain.Encounter;

import java.util.List;

@Dao
public interface EncounterRepository {
    @Query("SELECT * FROM encounter")
    List<Encounter> findByAll();

    @Query("SELECT * FROM encounter where  patient_id = :patientId AND  strftime('%M',date_visit) = :month AND strftime('%Y',date_visit) = :year AND regimen3 != ''")
    boolean findByCotrim(Long patientId, int month, int year);

    @Query("SELECT * FROM encounter WHERE facility_id =:facilityId  AND patient_id =:patientId  ORDER BY date_visit DESC LIMIT 1")
    Encounter getLastEncounter(Long facilityId,Long patientId);
    @Insert
    void save(Devolve devolve);

    @Update
    void update(Devolve devolve);
}
