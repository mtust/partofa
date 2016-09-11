package com.partofa.dto;

import lombok.Data;

/**
 * Created by tust on 11.09.2016.
 */
@Data
public class UserCreateDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

}
