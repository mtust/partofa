package com.partofa.repository;

import com.partofa.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mtust on 9/20/2016.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{

    public Region findByName(String name);
}
