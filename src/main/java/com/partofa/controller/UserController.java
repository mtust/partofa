package com.partofa.controller;

import com.partofa.domain.User;
import com.partofa.dto.*;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = {"admin/page/admin/edit/user","admin/edit/user"}, method = RequestMethod.POST)
    public RestMessageDTO editUser(UserEditDTO userEditDTO){
        return  userService.editUser(userEditDTO);
    }


//    @RequestMapping(name = "login", method = RequestMethod.POST)
//    public RestMessageDTO  login(UserLoginDTO userLoginDTO){
//
//        return userService.login(userLoginDTO);
//    }

    @RequestMapping(name = "public/user/signup", method = RequestMethod.POST)
    public ModelAndView createUser(UserRegistrationDTO userRegistrationDTO){
        log.info(userRegistrationDTO.toString());
        userService.signUp(userRegistrationDTO);
        return new ModelAndView("redirect:index");
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
    public UserDTO isUserAuthenticated() {
        return userService.getLoginUserDTO();
    }


}

