package com.interview.mockie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class QualificationDTO {
    private Long qualificationId;
    private Long userDetailId;
    private String degree;
    private String institute;
    private Integer yearOfPassing;
    private Double percentage;
}
