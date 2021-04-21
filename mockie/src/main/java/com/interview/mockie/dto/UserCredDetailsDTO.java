package com.interview.mockie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCredDetailsDTO {
    private Long credId;
    private String username;
    private String password;
    private String roles;
    private Long userDetailId;
}
