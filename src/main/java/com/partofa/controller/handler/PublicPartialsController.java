package com.partofa.controller.handler;


import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@Slf4j
public class PublicPartialsController {
    @Autowired
    private UserService userService;



//   @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String plainIndex() {
//
//        log.info("asasa");
//
//        return "index";
//    }

}
