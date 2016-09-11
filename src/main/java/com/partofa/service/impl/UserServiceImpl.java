package com.partofa.service.impl;

import com.partofa.domain.Role;
import com.partofa.domain.User;
import com.partofa.dto.*;
import com.partofa.exception.ObjectAlreadyExistException;
import com.partofa.repository.UserRepository;
import com.partofa.security.SecurityUtils;
import com.partofa.service.UserService;
import com.partofa.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tust on 09.09.2016.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
        }
        return user;
    }


    @Transactional
    @Override
    public User getCurrentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail);
        if (currentUser == null) {
            log.debug("anonymousUser detected");
            throw new RuntimeException();
        }
        return currentUser;
    }

    @Transactional
    @Override
    public RestMessageDTO signUp(UserRegistrationDTO userRegistrationDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (UserUtil.isNotFilledFieldsExist(userRegistrationDTO)) {
            throw new RuntimeException("Failed to create user, fill all required fields");
        }
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getPasswordConfirm())) {
            throw new RuntimeException("Failed to create user, passwords do not match");
        }
        User existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if (existingUser != null) {
            throw new ObjectAlreadyExistException("User already registered");
        }
        String hashedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
        User user = new User();
        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setPassword(hashedPassword);
        user.setRole(Role.ROLE_USER);
        user.setIsEnabled(false);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return new RestMessageDTO("Success", true);
    }

    @Transactional
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        users.forEach(user -> userDTOs.add(
                new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                        user.getRole() == Role.ROLE_USER ? "Оператор" : "Адміністратор",
                        user.getIsEnabled() == true ? "Активний" : "Неактивний")));
        return userDTOs;
    }

    @Transactional
    @Override
    public User getLoginUser() {
        String  userLogin = SecurityUtils.getCurrentUserLogin();

        return userRepository.findByEmail(userLogin);

    }

    @Transactional
    @Override
    public RestMessageDTO createUser(UserCreateDTO userRegistrationDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if (existingUser != null) {
            throw new ObjectAlreadyExistException("User already registered");
        }
        String hashedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
        User user = new User();
        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setPassword(hashedPassword);
        user.setRole(Role.getRole(userRegistrationDTO.getRole()));
        user.setIsEnabled(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return new RestMessageDTO("Success", true);
    }

    @Override
    public RestMessageDTO deleteUser(Long userId) {

        userRepository.delete(userId);

        return new RestMessageDTO("Success", true);
    }

    @Override
    public RestMessageDTO editUser(UserEditDTO userEditDTO) {

        User user = userRepository.findOne(userEditDTO.getId());
        log.info("user:" + user);
        log.info("userDTO" + userEditDTO);
        user.setEmail(userEditDTO.getEmail());
        user.setLastName(userEditDTO.getLastName());
        user.setRole(Role.getRole(userEditDTO.getRole()));
        user.setIsEnabled(userEditDTO.getIsEnabled());
        user.setFirstName(userEditDTO.getFirstName());

        userRepository.save(user);

        return new RestMessageDTO("Success", true);
    }
}
