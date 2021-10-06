package org.fhi360.ddd.dto;

import org.fhi360.ddd.domain.Facility;


import lombok.Data;

@Data
public class PatientDto {
    private Long id;
    private Facility facility;
    private PharmacyDto communityPharmacy;
    private Long pharmacyId;
    private String hospitalNum;

    private String uniqueId;

    private String surname;

    private String otherNames;

    private String gender;

    private String dateBirth;
    private String address;

    private String phone;

    private String uuid;

    private Boolean archived = false;

    private String dateStarted;

    private String lastClinicStage;

    private double lastViralLoad;

    private String dateLastViralLoad;

    private String viralLoadDueDate;

    private String viralLoadType;

    private String dateLastRefill;

    private String dateNextRefill;

    private String dateLastClinic;

    private String dateNextClinic;

    private int discontinued;
    private String outLetName;
    private String dateVisit;
}
