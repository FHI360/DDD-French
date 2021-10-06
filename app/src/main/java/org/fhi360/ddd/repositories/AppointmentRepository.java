package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Appointment;

import java.util.Date;

@Dao
public interface AppointmentRepository {
    @Insert
    void save(Appointment appointment);

    @Update
    void update(Appointment appointment);
    //facilityId, patientId, dateTracked
    @Query("SELECT id FROM appointment WHERE    patient_id=:patientId and date_tracked=:dateTracked")
    int getId(Long patientId, Date dateTracked);
}
