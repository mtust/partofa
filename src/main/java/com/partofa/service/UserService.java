package com.partofa.service;

import com.partofa.domain.User;
import com.partofa.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * Created by tust on 09.09.2016.
 */
@Service
public interface UserService {

    User getUserByEmail(String email);


}
