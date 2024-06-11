package com.ocs.medilabo_risk.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ocs.medilabo_risk.bean.NoteBean;
import com.ocs.medilabo_risk.bean.PatientBean;
import com.ocs.medilabo_risk.model.RiskLevel;

@Service
public class RiskAssessmentService {

    private static final String PATIENT_API_URL = "http://localhost:8081/patients/";
    private static final String NOTES_API_URL = "http://localhost:8083/notes/patient/";
    private static final List<String> TRIGGER_TERMS = Arrays.asList(
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse",
            "Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps"
    );

    private final RestTemplate restTemplate;

    public RiskAssessmentService() {
        this.restTemplate = new RestTemplate();
    }


    public String assessRiskLevel(Long patientId) {
        PatientBean patient = restTemplate.getForObject(PATIENT_API_URL + patientId, PatientBean.class);
        if (patient == null) {
            return null;
        }

        List<NoteBean> notes = Arrays.asList(restTemplate.getForObject(NOTES_API_URL + patientId, NoteBean[].class));

        return evaluateRiskLevel(patient, notes);
    }

    private String evaluateRiskLevel(PatientBean patient, List<NoteBean> notes) {
        int triggerCount = countTriggerTerms(notes);
        int age = calculateAge(patient.getDateDeNaissance());


        if (triggerCount == 0) {
            return "NONE";
        }

        if (age >= 30) {
            if (triggerCount >= 2 && triggerCount <= 5) {
                return "BORDERLINE";
            } else if (triggerCount >= 6 && triggerCount <= 7) {
                return "IN_DANGER";
            } else if (triggerCount >= 8) {
                return "EARLY_ONSET";
            }
        } else {
            if (patient.getGenre().equals("M")) {
                if (triggerCount == 3) {
                    return "IN_DANGER";
                } else if (triggerCount >= 5) {
                    return "EARLY_ONSET";
                }
            } else if (patient.getGenre().equals("F")) {
                if (triggerCount == 4) {
                    return "IN_DANGER";
                } else if (triggerCount >= 7) {
                    return "EARLY_ONSET";
                }
            }
        }

        return "BORDERLINE";
    }


    private int countTriggerTerms(List<NoteBean> notes) {
        int count = 0;
        for (NoteBean note : notes) {
            String noteText = note.getNote().toLowerCase();
            for (String term : TRIGGER_TERMS) {
                if (noteText.contains(term.toLowerCase())) {
                    count++;
                }
            }
        }
        return count;
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}