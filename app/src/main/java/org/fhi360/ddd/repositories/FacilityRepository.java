package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;

import java.util.List;

@Dao
public interface FacilityRepository {

    @Insert
    void save(Facility facility);

    @Update
    void update(Facility facility);


    @Query("SELECT * FROM facility where id = :id")
    Facility findOne(Long id);


    @Query("SELECT * FROM facility")
    List<Facility> findAll();


    @Query("SELECT * FROM facility LIMIT 1")
    Facility getFacility();

    @Insert
    void insertAll(Facility... facilities);


    @Query("delete from facility")
    void delete();

}
