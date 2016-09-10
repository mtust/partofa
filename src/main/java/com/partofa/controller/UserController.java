package com.partofa.controller;

import com.partofa.domain.User;
import com.partofa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by tust on 10.09.2016.
 */
@Controller
public class UserController {


    @Autowired
    UserService userService;


}
