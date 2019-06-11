package com.app.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    private Integer id;
    private String name;
    private String surname;
    private String pesel;
    private Boolean placebo;
    private String birthday;
    private String gender;
    private Integer doctorId;
}
