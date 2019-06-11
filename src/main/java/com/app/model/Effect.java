package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Effect {
    private Integer id;
    private String description;
    private String dateEffect;
    private Integer patientId;
    private Integer doctorId;

}
