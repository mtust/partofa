package com.partofa.util;

import com.partofa.dto.UserRegistrationDTO;
import org.assertj.core.util.Strings;

/**
 * Created by tust on 10.09.2016.
 */
public class UserUtil {

    public static  boolean isNotFilledFieldsExist(UserRegistrationDTO registrationDTO) {
        return Strings.isNullOrEmpty(registrationDTO.getFirstName()) ||
                Strings.isNullOrEmpty(registrationDTO.getLastName()) ||
                Strings.isNullOrEmpty(registrationDTO.getPassword()) ||
                Strings.isNullOrEmpty(registrationDTO.getPasswordConfirm()) ||
                Strings.isNullOrEmpty(registrationDTO.getEmail());
    }


}
