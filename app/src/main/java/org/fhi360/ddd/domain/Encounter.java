package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Entity
@Data
public class Encounter implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "facility_id")
    private int facilityId;
    @ColumnInfo(name = "patient_id")
    private Long patientId;
    @ColumnInfo(name = "date_visit")
    private Date dateVisit;
    @ColumnInfo(name = "question1")
    private String question1;
    @ColumnInfo(name = "question2")
    private String question2;
    @ColumnInfo(name = "question3")
    private String question3;
    @ColumnInfo(name = "question4")
    private String question4;
    @ColumnInfo(name = "question5")
    private String question5;
    @ColumnInfo(name = "question6")
    private String question6;
    @ColumnInfo(name = "question7")
    private String question7;
    @ColumnInfo(name = "question8")
    private String question8;
    @ColumnInfo(name = "question9")
    private String question9;
    @ColumnInfo(name = "regimentype")
    private String regimentype;
    @ColumnInfo(name = "regimen1")
    private String regimen1;
    @ColumnInfo(name = "regimen2")
    private String regimen2;
    @ColumnInfo(name = "regimen3")
    private String regimen3;
    @ColumnInfo(name = "regimen4")
    private String regimen4;
    @ColumnInfo(name = "duration1")
    private int duration1;
    @ColumnInfo(name = "duration2")
    private int duration2;
    @ColumnInfo(name = "duration3")
    private int duration3;
    @ColumnInfo(name = "duration4")
    private int duration4;
    @ColumnInfo(name = "prescribed1")
    private int prescribed1;
    @ColumnInfo(name = "prescribed2")
    private int prescribed2;
    @ColumnInfo(name = "prescribed3")
    private int prescribed3;
    @ColumnInfo(name = "prescribed4")
    private int prescribed4;
    @ColumnInfo(name = "dispensed1")
    private int dispensed1;
    @ColumnInfo(name = "dispensed2")
    private int dispensed2;
    @ColumnInfo(name = "dispensed3")
    private int dispensed3;
    @ColumnInfo(name = "dispensed4")
    private int dispensed4;
    @ColumnInfo(name = "notes")
    private String notes;
    @ColumnInfo(name = "next_refill")
    private Date nextRefill;
    @ColumnInfo(name = "time_stamp")
    private Date timeStamp;
}
