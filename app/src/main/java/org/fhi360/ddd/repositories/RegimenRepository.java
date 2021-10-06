package org.fhi360.ddd.repositories;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.PreLoadRegimen;
import org.fhi360.ddd.domain.Regimen;

import java.util.List;

@Dao
public interface RegimenRepository {
    @Query("SELECT * FROM regimen")
    List<Regimen> findByAll();

    @Query("SELECT * FROM regimen where id = :id")
    Regimen findByOne(Long id);

    //    @Query("SELECT * FROM regimen where (regimen_type_id >= 1 AND regimen_type_id <= 4) OR regimen_type_id = 14")
//    List<Regimen> getARV();
//
    // @Query("SELECT * FROM regimen where  id = 2005")
    @Query("SELECT * FROM regimen where (regimen_type_id >= 1 AND regimen_type_id <= 4)")
    List<Regimen> getARV();


    @Query("SELECT * FROM regimen where regimen_type_id = :regimentypeId")
    List<Regimen> getRegimens(Long regimentypeId);


    @Insert
    void save(Regimen regimen);

    @Insert
    void insertAll(Regimen... regimen);

    @Update
    void update(Regimen regimen);

    @Query("delete from regimen")
    void delete();


}
