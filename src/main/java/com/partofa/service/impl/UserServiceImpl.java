package com.partofa.service.impl;

import com.partofa.domain.User;
import com.partofa.repository.UserRepository;
import com.partofa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tust on 09.09.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            user = new User();
        }
        return user;
    }
}
