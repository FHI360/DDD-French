package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Pharmacy;

import java.util.List;

@Dao
public interface PharmacistAccountRepository {
    @Query("SELECT * FROM Pharmacy")
    List<Pharmacy> findByAll();

    @Query("SELECT * FROM Pharmacy where phone = :phone and email =:email")
    Pharmacy findByPhoneAndEmail(String phone, String email);

    @Query("SELECT * FROM Pharmacy")
    Pharmacy findbyOne();

    @Query("SELECT count(*) FROM Pharmacy")
    int count();

    @Insert
    void save(Pharmacy account);

    @Update
    void update(Pharmacy account);

    @Delete
    void delete(Pharmacy account);


    @Insert
    void insertAll(Pharmacy... pharmacies);

    @Query("SELECT * FROM Pharmacy where id = :id")
    Pharmacy findById(Long id);



}
