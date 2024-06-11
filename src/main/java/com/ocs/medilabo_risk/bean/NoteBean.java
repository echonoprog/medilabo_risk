package com.ocs.medilabo_risk.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteBean {
    private String id;
    private int patId;
    private String patient;
    private String note;
    // Getters, setters, constructeurs
}