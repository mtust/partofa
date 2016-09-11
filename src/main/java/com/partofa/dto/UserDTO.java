package com.partofa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by tust on 11.09.2016.
 */
@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String status;

}
