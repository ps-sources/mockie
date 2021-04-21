package com.interview.mockie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_cred_id")
    private UserCredDetails userCredDetails;

    // personal details
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 10)
    private Long mobileNumber;
    private String addressLine1;
    private String addressLine2;
    @Column(length = 6)
    private String postalCode;
    @Column(length = 150)
    private String emailId;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date dateOfBirth;

    // registration details
    private Integer batchYear;
    private String batchName;
    private Integer rollNumber;

    //qualification details
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_qualifications", joinColumns={@JoinColumn(name="user_id", referencedColumnName="user_id")}
            , inverseJoinColumns={@JoinColumn(name="qualification_id", referencedColumnName="qualif_id")})
    private Set<Qualification> qualifications;
}
