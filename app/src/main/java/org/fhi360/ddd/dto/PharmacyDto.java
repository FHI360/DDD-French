package org.fhi360.ddd.dto;


import org.fhi360.ddd.domain.Facility;

import lombok.Data;

@Data
public class PharmacyDto {
    private Long id;
    private String name;
    private Facility facility;
    private String address;
    private String phone;
    private String email;
    private String type;
    private String pin;
    private String username;

}
