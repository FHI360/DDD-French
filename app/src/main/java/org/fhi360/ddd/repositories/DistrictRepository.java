package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;

import org.fhi360.ddd.domain.District;

@Dao
public interface DistrictRepository {

    @Insert
    void insertAll(District... districts);
    @Insert
    void save(District districts);
}
