package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import org.fhi360.ddd.domain.Drug;
import java.util.List;

@Dao
public interface DrugRepository {
    @Insert
    void save(Drug drug);

    @Update
    void update(Drug drug);

    @Query("SELECT * FROM drug WHERE   drug_name = :drugName")
    Drug findByDrugName(String drugName);

    @Query("SELECT * FROM drug WHERE   id = :id")
    Drug findOne(int id);

    @Query("SELECT * FROM drug")
    List<Drug> findByAll();



}
