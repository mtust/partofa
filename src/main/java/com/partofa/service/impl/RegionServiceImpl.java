package com.partofa.service.impl;

import com.partofa.domain.Region;
import com.partofa.dto.RestMessageDTO;
import com.partofa.repository.RegionRepository;
import com.partofa.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mtust on 9/20/2016.
 */
@Service
@Slf4j
public class RegionServiceImpl implements RegionService {

    @Autowired
    RegionRepository regionRepository;

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public RestMessageDTO createRegion(String name) {

        Region region = new Region();
        region.setName(name);
        regionRepository.save(region);
        return new RestMessageDTO("Success", true);
    }
}
