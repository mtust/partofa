package com.partofa.dto;

import lombok.Data;

/**
 * Created by tust on 10.09.2016.
 */
@Data
public class UserRegistrationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirm;

}
