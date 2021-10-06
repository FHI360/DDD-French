package org.fhi360.ddd.repositories;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.PreLoadRegimen;

import java.util.List;

@Dao
public interface PatientRepository {

    @Query("SELECT * FROM patient")
    List<Patient> findByAll();

    @Query("SELECT * FROM patient where date_started BETWEEN  :to AND  :from")
    List<Patient> dateRange(String to, String from);

    @Query("SELECT * FROM patient")
    Cursor getAllPatient();


    @Query("SELECT * FROM patient where id = :id")
    Patient findOne(Long id);

    @Query("SELECT count(*) FROM patient where gender =:femaleGender")
    int genderCount(String femaleGender);

    @Query("SELECT count(*) FROM patient where pharmacy_id =:pharmacyId")
    int count(Long pharmacyId);


    @Query("SELECT * FROM patient where unique_id = :hospitalNum and facility_id =:facilityId")
    boolean findHospitalNum(String hospitalNum, Long facilityId);


    @Query("SELECT * FROM patient WHERE  id =:id")
    Patient findByPatient(Long id);


    @Query("SELECT * FROM patient")
    List<Patient> findByAll1();

    @Insert
    void save(Patient patients);

    @Update
    void update(Patient patients);

//    @Update
//    void updateUsers(Patient... patients);


    @Insert
    void insertAll(Patient... patients);

    @Query("SELECT  *  FROM patient WHERE CURRENT_TIME > date_next_refill ORDER BY surname ASC")
    List<Patient> getDefaulters();

    @Query("SELECT  count(*)  FROM patient WHERE CURRENT_TIME > date_next_refill ORDER BY surname ASC")
    int getDefaultersCount();

    @Query("SELECT  count(*)  FROM patient WHERE facility_id =:facilityId")
    int total(Long facilityId);

    @Query("DELETE    FROM patient WHERE id =:id")
    void delete(Long id );

    @Query("SELECT  *  FROM patient WHERE unique_id =:hosNum")
    Patient checkIfClientExist(String hosNum);
//
//
//    @Query("Update patient set discontinued =:discontinued, date_discontinue =:dateDiscontinue where id =:id")
//    void updateDiscontinue(int discontinued, String dateDiscontinue, Long id);

    @Query("SELECT  *  FROM patient WHERE CURRENT_TIME - date_next_refill >:period and surname LIKE :name OR other_names LIKE :name ORDER BY surname ASC")
    List<Patient> getDefaulters(String period, String name);

    @Query("UPDATE patient set date_next_refill = :dateNextRefill where id =:id")
    void updateDateNextRefil(String dateNextRefill, Long id);

}
