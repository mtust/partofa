package com.partofa.controller;

import com.partofa.domain.Region;
import com.partofa.domain.User;
import com.partofa.dto.*;
import com.partofa.exception.ObjectAlreadyExistException;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
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
    public void createUser(UserRegistrationDTO userRegistrationDTO){
        log.info(userRegistrationDTO.toString());
        userService.signUp(userRegistrationDTO);

        return;
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


    @RequestMapping(value = {"me"}, method = RequestMethod.GET)
    public UserDTO getMe(){
        return userService.getLoginUserDTO();
    }


    @RequestMapping(value = "delete/user", method = RequestMethod.DELETE)
    public RestMessageDTO deleteUser(@RequestParam(value = "id") Long userId){
        log.info("userID for delete:" + userId);
        return userService.deleteUser(userId);
    }

    @RequestMapping(value = "block/user", method = RequestMethod.POST)
    public RestMessageDTO blockUser(@RequestParam(value = "id") Long userId){
        return userService.blockUser(userId);
    }

    @RequestMapping(value = {"admin/page/private/user/is-authenticated", "private/user/is-authenticated"}, method = RequestMethod.GET)
    public UserDTO isUserAuthenticated() {
        return userService.getLoginUserDTO();
    }

    @RequestMapping(value = "edit/me", method = RequestMethod.POST)
    public RestMessageDTO editMyInfo(UserEditDTO userEditDTO){
       return userService.editUserMe(userEditDTO);
    }

    @RequestMapping(value = "edit/password", method = RequestMethod.POST)
    public RestMessageDTO changePassword(ChangePasswordDTO changePasswordDTO){
        return userService.changePassword(changePasswordDTO);
    }

    @RequestMapping(value = {"photo/edit", "private/me/edit/photo"}, headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    public RestMessageDTO changePhoto(@RequestParam("photo") MultipartFile photo) throws IOException {
        log.info(photo.toString());
        return userService.changePhoto(photo);
    }

    @RequestMapping(value = "get/photo", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto() throws IOException, SQLException {
        return userService.getUserPhoto();
    }

    @RequestMapping(value = "public/password/restore", method = RequestMethod.POST)
    @ResponseBody
    public RestMessageDTO restorePassword(@RequestParam(value = "email") String email){
        return userService.sendEmailWithPassword(email);
    }

    @RequestMapping(value = "private/user/regions", method = RequestMethod.GET)
    @ResponseBody
    public List<Region> getUserRegions(){
        return userService.getUserRegions();
    }








}

