package org.fhi360.ddd.dto;

import org.fhi360.ddd.domain.District;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Regimen;
import org.fhi360.ddd.domain.State;

import java.util.List;

@lombok.Data
public class Data {
    private Facility facility;
    private State state;
    private List<District> district;
    private List<Regimen> regimens;
    private PharmacyDto pharmacy;
    private List<PatientDto> patients;
    private String message;
    private List<PharmacyDto> communityPharmacies;

}
