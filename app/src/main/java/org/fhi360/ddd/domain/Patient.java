package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Patient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "facility_id")
    private Long facilityId;
    @ColumnInfo(name = "hospital_num")
    private String hospitalNum;
    @ColumnInfo(name = "unique_id")
    private String uniqueId;
    @ColumnInfo(name = "surname")
    private String surname;
    @ColumnInfo(name = "other_names")
    private String otherNames;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "date_birth")
    private String dateBirth;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "date_started")
    private String dateStarted;
    @ColumnInfo(name = "last_clinic_stage")
    private String lastClinicStage;
    @ColumnInfo(name = "regimen_type")
    private String regimenType;
    @ColumnInfo(name = "regimen_id")
    private String regimen;
    @ColumnInfo(name = "last_viral_load")
    private double lastViralLoad;
    @ColumnInfo(name = "date_last_viral_load")
    private String dateLastViralLoad;
    @ColumnInfo(name = "viral_load_due_date")
    private String viralLoadDueDate;
    @ColumnInfo(name = "viralLoad_type")
    private String viralLoadType;
    @ColumnInfo(name = "dateLast_refill")
    private String dateLastRefill;
    @ColumnInfo(name = "date_next_refill")
    private String dateNextRefill;
    @ColumnInfo(name = "date_last_clinic")
    private String dateLastClinic;
    @ColumnInfo(name = "date_next_clinic")
    private String dateNextClinic;
    @ColumnInfo(name = "discontinued")
    private int discontinued;
    @ColumnInfo(name = "pharmacy_id")
    private Long pharmacyId;
    @ColumnInfo(name = "status")
    private int status;
    @ColumnInfo(name = "uuid")
    private String uuid;
    @ColumnInfo(name = "outlet_name")
    private String outLetName;

    public Patient(Long id) {
        this.id = id;
    }
}
