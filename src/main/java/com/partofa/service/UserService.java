package com.partofa.service;

import com.partofa.domain.User;
import com.partofa.dto.RestMessageDTO;
import com.partofa.dto.UserCreateDTO;
import com.partofa.dto.UserDTO;
import com.partofa.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tust on 09.09.2016.
 */
@Service
public interface UserService {

    User getUserByEmail(String email);


    User getCurrentUser();

    @Transactional
    RestMessageDTO signUp(UserRegistrationDTO userRegistrationDTO);
    List<UserDTO> getAllUsers();
    User getLoginUser();

    RestMessageDTO createUser(UserCreateDTO userRegistrationDTO);

    RestMessageDTO deleteUser(Long userId);
}
