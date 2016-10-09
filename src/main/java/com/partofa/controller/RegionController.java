package com.partofa.controller;

import com.partofa.domain.Region;
import com.partofa.dto.RestMessageDTO;
import com.partofa.service.RegionService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mtust on 9/20/2016.
 */

@RestController
@Slf4j
public class RegionController {

    @Autowired
    RegionService regionService;

    @RequestMapping(value = {"regions", "admin/page/regions", "private/page/regions"}, method = RequestMethod.GET)
    public List<Region> getAllRegions(){
        return regionService.getAllRegions();
    }

    @RequestMapping(value = {"admin/region/add", "admin/page/admin/region/add"}, method = RequestMethod.POST)
    public RestMessageDTO createRegion(@RequestParam("name") String name){
        return regionService.createRegion(name);
    }

    @RequestMapping(value = "admin/region/delete", method = RequestMethod.DELETE)
    public RestMessageDTO deleteRegion(@RequestParam(value = "id") Long id){
        return regionService.deleteRegion(id);
    }

}
