package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;

import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.State;

@Dao
public interface StateRepository {

    @Insert
    void insertAll(State... states);

    @Insert
    void save(State states);
}
