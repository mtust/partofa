package com.partofa.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * Created by tust on 09.09.2016.
 */

@Slf4j
@Data
@Entity
@Table(name = "partofa_user")
public class User {

    @Id
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private Boolean isEnabled;
    @Enumerated(EnumType.STRING)
    private Role role;

}
