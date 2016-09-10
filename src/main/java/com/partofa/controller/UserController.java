package com.partofa.controller;

import com.partofa.domain.User;
import com.partofa.dto.RestMessageDTO;
import com.partofa.dto.UserRegistrationDTO;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Slf4j
@Controller
public class UserController {


    @Autowired
    UserService userService;


    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getAllUsers();
    }


    @RequestMapping(name = "public/user/signup", method = RequestMethod.POST)
    public RestMessageDTO createUser(UserRegistrationDTO userRegistrationDTO){
        log.info(userRegistrationDTO.toString());
        return userService.signUp(userRegistrationDTO);
    }

    @RequestMapping(value = "private/user/is-authenticated", method = RequestMethod.GET)
    public User isUserAuthenticated() {
        return userService.getLoginUser();
    }
}

