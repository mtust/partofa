package com.partofa.service;

import com.partofa.domain.Region;
import com.partofa.dto.RestMessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mtust on 9/20/2016.
 */
@Service
public interface RegionService {

    List<Region> getAllRegions();

    RestMessageDTO createRegion(String name);

    RestMessageDTO deleteRegion(Long id);

}
