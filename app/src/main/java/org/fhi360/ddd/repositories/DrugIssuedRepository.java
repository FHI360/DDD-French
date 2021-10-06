package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.IssuedDrug;

import java.util.Date;
import java.util.List;

@Dao
public interface DrugIssuedRepository {
    @Insert
    void save(IssuedDrug drug);

    @Update
    void update(IssuedDrug drug);

    @Query("SELECT * FROM issueddrug")
    List<IssuedDrug> findByAll();


    @Query("SELECT * FROM issueddrug WHERE    drug_id=:drugId and pin_code=:pinCode")
    IssuedDrug findByAllBYId(Long drugId, String pinCode);
}
