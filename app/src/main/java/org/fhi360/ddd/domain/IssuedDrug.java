package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;

@Data
@Entity
public class IssuedDrug implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "pin_code")
    private String pinCode;
    @ColumnInfo(name = "drug_id")
    private Long regimenId;
    @ColumnInfo(name = "batch_number")
    private String batchNumber;
    @ColumnInfo(name = "expired_date")
    private String expireDate;
    @ColumnInfo(name = "quantity")
    private double quantity;


}
