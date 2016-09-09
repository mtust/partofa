package com.partofa.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by tust on 09.09.2016.
 */

@Slf4j
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private Set<Role> roles;

}
