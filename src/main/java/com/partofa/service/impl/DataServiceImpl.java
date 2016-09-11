package com.partofa.service.impl;

import com.partofa.domain.Data;
import com.partofa.dto.RestMessageDTO;
import com.partofa.repository.DataRepository;
import com.partofa.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService {

    @Autowired
    DataRepository dataRepository;

    @Transactional
    @Override
    public List<Data> getAllData() {
        dataRepository.findAll().forEach(x -> log.info(x.toString()));
        return dataRepository.findAll();
    }

    @Transactional
    @Override
    public RestMessageDTO editData(Data data) {
        dataRepository.save(data);
        return new RestMessageDTO("Success", true);
    }

    @Transactional
    @Override
    public RestMessageDTO deleteData(Long dataId) {
        Data data = dataRepository.findOne(dataId);
        data.setDelDate(new Timestamp(new Date().getTime()));
        dataRepository.save(data);
        return new RestMessageDTO("Success", true);
    }

    @Transactional
    @Override
    public RestMessageDTO createData(Data data) {
        dataRepository.save(data);
        return new RestMessageDTO("Success", true);
    }

    @Transactional
    @Override
    public List<Data> getNonDeletedData() {
        return dataRepository.findByDelDateIsNull();
    }

    @Transactional
    @Override
    public List<Data> getDeletedData() {
        return dataRepository.findByDelDateIsNotNull();
    }
}
