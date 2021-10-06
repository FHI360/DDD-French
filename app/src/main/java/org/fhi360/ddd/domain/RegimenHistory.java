package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;

@Data
@Entity
public class RegimenHistory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "regimen")
    private String regimen;
    @ColumnInfo(name = "patient_id")
    private int patientId;
    @ColumnInfo(name = "duration")
    private int duration;
    @ColumnInfo(name = "quantity_prescribed")
    private String quantityPrescribed;
    @ColumnInfo(name = "quantity_dispensed")
    private String quantityDispensed;
    @ColumnInfo(name = "date_visit")
    private String dateVisit;
    @ColumnInfo(name = "date_visit_next_refill")
    private String dateNextRefill;
}
