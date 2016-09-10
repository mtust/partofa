package com.partofa.repository;

import com.partofa.domain.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tust on 10.09.2016.
 */
public interface DataRepository extends JpaRepository<Data, Long> {


}
