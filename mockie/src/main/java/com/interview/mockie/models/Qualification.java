package com.interview.mockie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "qualifications")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Qualification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "qualif_id")
    private Long qualificationId;

    @ManyToOne
    private UserDetail userDetail;

    private String degree;
    private String institute;
    private Integer yearOfPassing;
    private Double percentage;
}
