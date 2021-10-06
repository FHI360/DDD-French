package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
public class Devolve implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "facility_id")
    private Long facilityId;
    @ColumnInfo(name = "patient_id")
    private Long patientId;
    @ColumnInfo(name = "date_devolved")
    private Date dateDevolved;
    @ColumnInfo(name = "viral_load_assessed")
    private String viralLoadAssessed;
    @ColumnInfo(name = "last_viral_load")
    private double lastViralLoad;
    @ColumnInfo(name = "date_last_viralLoad")
    private Date dateLastViralLoad;
    @ColumnInfo(name = "cd4_assessed")
    private String cd4Assessed;
    @ColumnInfo(name = "last_cd4")
    private double lastCd4;
    @ColumnInfo(name = "date_last_cd4")
    private Date dateLastCd4;
    @ColumnInfo(name = "last_clinic_stage")
    private String lastClinicStage;
    @ColumnInfo(name = "date_last_clinic_stage")
    private Date dateLastClinicStage;
    @ColumnInfo(name = "arv_dispensed")
    private String arvDispensed;
    @ColumnInfo(name = "regimentype")
    private String regimentype;
    @ColumnInfo(name = "regimen")
    private String regimen;
    @ColumnInfo(name = "date_last_refill")
    private Date dateLastRefill;
    @ColumnInfo(name = "date_next_refill")
    private Date dateNextRefill;
    @ColumnInfo(name = "date_last_clinic")
    private Date dateLastClinic;
    @ColumnInfo(name = "date_next_clinic")
    private Date dateNextClinic;
    @ColumnInfo(name = "note")
    private String notes;
    @ColumnInfo(name = "date_discontinued")
    private String dateDiscontinued;
    @ColumnInfo(name = "reason_discontinued")
    private String reasonDiscontinued;



}
