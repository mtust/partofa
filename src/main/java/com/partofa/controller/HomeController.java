package com.partofa.controller;

import com.partofa.domain.Data;
import com.partofa.domain.User;
import com.partofa.dto.CreateDataDTO;
import com.partofa.dto.DataDTO;
import com.partofa.dto.EditDataDTO;
import com.partofa.dto.RestMessageDTO;
import com.partofa.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
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
    public List<Data> homePage() {
        log.info("log1");
        return dataService.getAllData();
    }

    @RequestMapping(value = "private/home/actualData", method = RequestMethod.GET)
    public List<DataDTO> getNonDeletedData() {
        return dataService.getNonDeletedData();
    }

    @RequestMapping(value = "private/home/deletedData", method = RequestMethod.GET)
    public List<DataDTO> getDeletedData() {
        return dataService.getDeletedData();
    }

    @RequestMapping(value = {"private/page/private/edit", "private/edit"}, method = RequestMethod.POST)
    public RestMessageDTO editData(EditDataDTO editDataDTO) {
        log.info("edit put:");
//        log.info("createDataDTO" + createDataDTO.toString());
//        log.info("id: " + id);
        log.info(editDataDTO.toString());
        return dataService.editData(editDataDTO);
    }

    @RequestMapping(value = {"private/page/private/delete", "private/delete"}, method = RequestMethod.DELETE)
    public RestMessageDTO deleteData(@RequestParam(value = "id") Long dataId) {
        return dataService.deleteData(dataId);
    }

    @RequestMapping(value = {"private/page/private/add", "private/add"}, method = RequestMethod.POST)
    public RestMessageDTO createData(CreateDataDTO createDataDTO) {
        return dataService.createData(createDataDTO);
    }

    @RequestMapping(value = {"admin/revert", "private/page/admin/revert"}, method = RequestMethod.POST)
    public RestMessageDTO revertData(@RequestParam(value = "id") Long id){
        return dataService.revertData(id);
    }
}
