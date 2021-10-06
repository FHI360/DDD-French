package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;;
import java.util.Date;

import kotlin.jvm.Transient;
import lombok.Data;

@Data
@Entity
public class ARV {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "patient_id")
    private Patient patient;
    @ColumnInfo(name = "facility_id")
    private Long facilityId;
    @ColumnInfo(name = "date_visit")
    private String dateVisit;
    @ColumnInfo(name = "date_next_refill")
    private String dateNextRefill;
    @ColumnInfo(name = "body_weight")
    private String bodyWeight;
    @ColumnInfo(name = "height")
    private String height;
    @ColumnInfo(name = "bp")
    private String bp;
    @ColumnInfo(name = "bmi")
    private String bmi;
    @ColumnInfo(name = "bmi_category")
    private String bmiCategory;
    @ColumnInfo(name = "itp")
    private String itp;
    @ColumnInfo(name = "tbb_treatment")
    private String tbTreatment;
    @ColumnInfo(name = "date_started_tb_treatment")
    private String dateStartedTbTreatment;
    @ColumnInfo(name = "have_you_been_coughing")
    private String haveYouBeenCoughing;
    @ColumnInfo(name = "do_you_have_fever")
    private String doYouHaveFever;
    @ColumnInfo(name = "are_you_losing_weight")
    private String areYouLosingWeight;
    @ColumnInfo(name = "are_you_having_sweet")
    private String areYouHavingSweet;
    @ColumnInfo(name = "do_you_have_swelling_neck")
    private String doYouHaveSwellingNeck;
    @ColumnInfo(name = "tb_referred")
    private String tbReferred;
    @ColumnInfo(name = "eligible_ipt")
    private String eligibleIpt;
    @ColumnInfo(name = "regimen1")
    private Long regimen1;
    @ColumnInfo(name = "duration1")
    private int duration1;
    @ColumnInfo(name = "prescribed1")
    private String prescribed1;
    @ColumnInfo(name = "dispensed1")
    private String dispensed1;
    @ColumnInfo(name = "regimen2")
    private Long regimen2;
    @ColumnInfo(name = "duration2")
    private int duration2;
    @ColumnInfo(name = "prescribed2")
    private String prescribed2;
    @ColumnInfo(name = "dispensed2")
    private String dispensed2;
    @ColumnInfo(name = "regimen3")
    private Long regimen3;
    @ColumnInfo(name = "duration3")
    private int duration3;
    @ColumnInfo(name = "prescribed3")
    private String prescribed3;
    @ColumnInfo(name = "dispensed3")
    private String dispensed3;
    @ColumnInfo(name = "regimen4")
    private String regimen4;
    @ColumnInfo(name = "duration4")
    private int duration4;
    @ColumnInfo(name = "prescribed4")
    private String prescribed4;
    @ColumnInfo(name = "dispensed4")
    private String dispensed4;
    @ColumnInfo(name = "adverseIssue")
    private String adverseIssue;
    @ColumnInfo(name = "adverseReport")
    private String adverseReport;
    @ColumnInfo(name = "date_next_clinic")
    private String dateNextClinic;
    @ColumnInfo(name = "viral_load_deu_date")
    private String viralLoadDeuDate;
    @ColumnInfo(name = "missed_refill")
    private String missedRefill;
    @ColumnInfo(name = "how_many_refill_missed")
    private String howMany;
    @ColumnInfo(name = "uuid")
    private String uuid;
    private String temperature;
    private String dateDiscontinued;
    private String reasonDiscontinued;
    private int discontinued;

}
