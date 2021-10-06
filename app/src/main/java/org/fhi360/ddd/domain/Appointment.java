package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "facility_id")
    private int facilityId;
    @ColumnInfo(name = "patient_id")
    private Long patientId;
    @ColumnInfo(name = "date_tracked")
    private Date dateTracked;
    @ColumnInfo(name = "type_tracking")
    private String typeTracking;
    @ColumnInfo(name = "tracking_outcome")
    private String trackingOutcome;
    @ColumnInfo(name = "date_last_visit")
    private Date dateLastVisit;
    @ColumnInfo(name = "date_next_visit")
    private Date dateNextVisit;
    @ColumnInfo(name = "date_agreed")
    private Date dateAgreed;
    @ColumnInfo(name = "communitypharm_id")
    private int communitypharmId;



}
