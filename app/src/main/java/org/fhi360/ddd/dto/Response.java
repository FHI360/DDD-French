package org.fhi360.ddd.dto;

import org.fhi360.ddd.domain.Pharmacy;
import org.fhi360.ddd.domain.User;

@lombok.Data
public class Response {
    private String message;
    private PharmacyDto pharmacy;
    private PatientDto patient;
    private UserDto user;
}
