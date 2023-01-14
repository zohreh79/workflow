package com.payeshgaran.workflow.model;

import lombok.*;

@Getter
@Setter
public class QuestionnaireDto {

    private String medicalNumber;

    private String doctorHelp;

    private Long surgeryNoInyear;

    private boolean harmPurporst;

    private String clinicAddress;

    private String clinicTelephone;

    private String hospitalAddress;

    private String hospitalTelephone;

    private String homeAddress;

    private String homeTelephone;

    private boolean resident;

    private boolean plasticSurgeryCover;

    private Float residentAmount;

    public QuestionnaireDto(String medicalNumber, String doctorHelp, Long surgeryNoInyear, boolean harmPurporst, String clinicAddress, String clinicTelephone, String hospitalAddress, String hospitalTelephone, String homeAddress, String homeTelephone, String hospitalTelephone1, String homeAddress1, String homeTelephone1, boolean resident, boolean plasticSurgeryCover, Float residentAmount) {
        this.medicalNumber = medicalNumber;
        this.doctorHelp = doctorHelp;
        this.surgeryNoInyear = surgeryNoInyear;
        this.harmPurporst= harmPurporst;
        this.clinicAddress = clinicAddress;
        this.clinicTelephone = clinicTelephone;
        this.hospitalAddress = hospitalAddress;
        this.hospitalTelephone = hospitalTelephone;
        this.homeAddress = homeAddress;
        this.homeTelephone = homeTelephone;
        this.resident = resident;
        this.plasticSurgeryCover = plasticSurgeryCover;
        this.residentAmount = residentAmount;
    }
}