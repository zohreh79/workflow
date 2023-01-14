package com.payeshgaran.workflow.model;

import lombok.*;
import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Questionnaire implements Serializable{

    @Serial
    private static final long serialVersionUID = 476071435017361022L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String taskId;

    private String executionId;

    private String assignee;

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

}