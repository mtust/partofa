package com.partofa.controller;

import com.partofa.domain.User;
import com.partofa.dto.RestMessageDTO;
import com.partofa.dto.UserCreateDTO;
import com.partofa.dto.UserDTO;
import com.partofa.dto.UserRegistrationDTO;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Slf4j
@RestController
public class UserController {


    @Autowired
    UserService userService;


    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }


    @RequestMapping(value = {"admin/page/admin/create/user","admin/create/user"}, method = RequestMethod.POST)
    public RestMessageDTO addUser(UserCreateDTO userCreateDTO){
        return  userService.createUser(userCreateDTO);
    }


    @RequestMapping(name = "public/user/signup", method = RequestMethod.POST)
    public RestMessageDTO createUser(UserRegistrationDTO userRegistrationDTO){
        log.info(userRegistrationDTO.toString());
        return userService.signUp(userRegistrationDTO);
    }


    @RequestMapping(name = "private/getCurrentUserRole", method = RequestMethod.GET)
    public String getCurrentUserRole(){
        User user = null;
        try {
            user = userService.getLoginUser();
        } catch (NullPointerException e){
            log.info("anonymus user");
        }

        if(user != null){
            return user.getRole().getParamName();
        } else {
            return "error";
        }

    }


    @RequestMapping(value = {"admin/page/admin/delete/user", "admin/delete/user"}, method = RequestMethod.DELETE)
    public RestMessageDTO deleteUser(@RequestParam(value = "id") Long userId){
        log.info("userID for delete:" + userId);
        return userService.deleteUser(userId);
    }

    @RequestMapping(value = "private/user/is-authenticated", method = RequestMethod.GET)
    public User isUserAuthenticated() {
        return userService.getLoginUser();
    }
}

