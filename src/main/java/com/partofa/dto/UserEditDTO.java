package com.partofa.dto;

import lombok.Data;

/**
 * Created by tust on 11.09.2016.
 */
@Data
public class UserEditDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Boolean isEnabled;
    private String region;
    private String password;
}
