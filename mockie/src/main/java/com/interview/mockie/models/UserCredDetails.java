package com.interview.mockie.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "u_credentials")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserCredDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cred_id")
    private Long credId;

    @Column(length = 50, unique = true)
    private String username;
    //@JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role roles;

    @OneToOne(mappedBy = "userCredDetails")
    UserDetail userDetail;

    public UserCredDetails(Long credId, String username, String password, Role roles) {
        this.credId = credId;
        this.username = username;
        this.password = password;
        this.roles = Role.USER;
    }

    public UserCredDetails(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = Role.USER;
    }
}
