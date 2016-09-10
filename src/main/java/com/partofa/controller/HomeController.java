package com.partofa.controller;

import com.partofa.domain.Data;
import com.partofa.domain.User;
import com.partofa.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tust on 09.09.2016.
 */
@RestController
@Slf4j
public class HomeController {


    @Autowired
    DataService dataService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public List<Data> homePage(){
        log.info("log1");
        return dataService.getAllData();
    }




}
