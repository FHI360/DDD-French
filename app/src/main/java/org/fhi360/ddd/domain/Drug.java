package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;

@Data
@Entity
public class Drug implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "drug_name")
    private String drugName;
    @ColumnInfo(name = "basic_unit")
    private String basicUnit;
    @ColumnInfo(name = "regime_id")
    private Long regimeId;


}
