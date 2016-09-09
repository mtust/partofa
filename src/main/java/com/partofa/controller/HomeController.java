package com.partofa.controller;

import com.partofa.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tust on 09.09.2016.
 */
@Controller
@Slf4j
public class HomeController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(){
        log.info("log1");
        return "index.html";
    }

}
