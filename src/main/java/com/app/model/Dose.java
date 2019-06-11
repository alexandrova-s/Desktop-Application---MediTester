package com.app.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dose {
    private Integer id;
    private Double doseValue;
    private String dateDose;
    private Integer patientId;
    private Integer doctorId;
}
