package com.partofa.service.impl;

import com.partofa.domain.Data;
import com.partofa.repository.DataRepository;
import com.partofa.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService{

    @Autowired
    DataRepository dataRepository;

    @Transactional
    public List<Data> getAllData(){
        dataRepository.findAll().forEach(x -> log.info(x.toString()));
        return dataRepository.findAll();
    }

}
