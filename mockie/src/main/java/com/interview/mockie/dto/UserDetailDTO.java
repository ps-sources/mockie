package com.interview.mockie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDTO {
    private Long userId;
    private UserCredDetailsDTO userCredDetails;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String emailId;
    private Date dateOfBirth;
    private Integer batchYear;
    private String batchName;
    private Integer rollNumber;
    private Set<QualificationDTO> qualifications;
}
