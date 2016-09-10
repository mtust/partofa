package com.partofa.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Email;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Email
    private String email;
    private String password;
    private Boolean isEnabled;
    @Enumerated(EnumType.ORDINAL)
    private Role role;

}
