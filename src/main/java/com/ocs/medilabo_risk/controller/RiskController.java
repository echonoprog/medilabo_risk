package com.ocs.medilabo_risk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocs.medilabo_risk.model.RiskLevel;
import com.ocs.medilabo_risk.service.RiskAssessmentService;

@RestController
@RequestMapping("/risk")
public class RiskController {

    @Autowired
    private RiskAssessmentService riskAssessmentService;

    @GetMapping("/{id}")
    public String getRiskLevelForPatient(@PathVariable Long id) {
        String riskLevel = riskAssessmentService.assessRiskLevel(id);
        return riskLevel;
    }
}