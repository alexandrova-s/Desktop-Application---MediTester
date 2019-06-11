package com.app.model;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private Integer id;
    private String name;
    private String surname;
    private String medicalLicence;
    private String phoneNumber;
}
