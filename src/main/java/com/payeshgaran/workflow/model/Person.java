package com.payeshgaran.workflow.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -8250050359721309969L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long priority;

    private String userId;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

}