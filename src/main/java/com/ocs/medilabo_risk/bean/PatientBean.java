package com.ocs.medilabo_risk.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PatientBean {
    private Long id;
    private String prenom;
    private String nom;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDeNaissance;
    private String genre;
    private String adressePostale;
    private String numeroTelephone;

}