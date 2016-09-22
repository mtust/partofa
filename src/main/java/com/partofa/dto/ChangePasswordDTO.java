package com.partofa.dto;

import lombok.Data;

/**
 * Created by tust on 21.09.2016.
 */

@Data
public class ChangePasswordDTO {

    String password;
    String newPassword;
    String newPasswordConfirm;

}
