package com.partofa.controller.handler;


import com.partofa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PublicPartialsController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String plainIndex() {
        return "/index";
    }

//    @RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
//    public String favicon() {
//        return null;
//    }

    @RequestMapping(value = "/public/login", method = RequestMethod.GET)
    public String login() {
        return "/public/login";
    }

    @RequestMapping(value = "/public/forgot", method = RequestMethod.GET)
    public String forgot() {
        return "/public/forgot";
    }

    @RequestMapping(value = "/directives/public/header", method = RequestMethod.GET)
    public String headerDirective() {
        return "/directives/public/header";
    }

    @RequestMapping(value = "/public/sign-up", method = RequestMethod.GET)
    public String sigUp() {
        return "/public/sign-up";
    }

    @RequestMapping(value = "/public/error", method = RequestMethod.GET)
    public String error() {
        return "/error";
    }

    @RequestMapping(value = "/additional/header", method = RequestMethod.GET)
    public String header() {
        return "/additional/header";
    }

}
