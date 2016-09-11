package com.partofa.controller;

import com.partofa.domain.Data;
import com.partofa.domain.User;
import com.partofa.dto.RestMessageDTO;
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

    @RequestMapping(value = "private/home", method = RequestMethod.GET)
    public List<Data> homePage(){
        log.info("log1");
        return dataService.getAllData();
    }

    @RequestMapping(value = "private/home/actualData", method = RequestMethod.GET)
    public List<Data> getNonDeletedData(){
        return dataService.getNonDeletedData();
    }

    @RequestMapping(value = "private/home/deletedData", method = RequestMethod.GET)
    public List<Data> getDeletedData(){
        return dataService.getDeletedData();
    }

    @RequestMapping(value = "private/edit", method = RequestMethod.PUT)
    public RestMessageDTO editData(Data data){
        return dataService.editData(data);
    }

    @RequestMapping(value = "private/delete", method = RequestMethod.DELETE)
    public RestMessageDTO deleteData(Long dataId){
        return dataService.deleteData(dataId);
    }

    @RequestMapping(value = "private/add", method = RequestMethod.POST)
    public RestMessageDTO createData(Data data){
        return dataService.createData(data);
    }




}
