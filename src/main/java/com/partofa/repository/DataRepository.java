package com.partofa.repository;

import com.partofa.domain.Data;
import com.partofa.domain.Region;
import com.partofa.dto.DataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Repository
public interface DataRepository extends JpaRepository<Data, Long> {


    List<Data> findByDelDateIsNull();


    List<Data> findByDelDateIsNotNull();

    List<Data> findByDelDateIsNullAndRegion(Region region);

    List<Data> findByDelDateIsNotNullAndRegion(Region region);

    List<Data> findByRegionId(Long regionId);


}
