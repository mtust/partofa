package com.partofa.service.impl;

import com.partofa.domain.Data;
import com.partofa.domain.User;
import com.partofa.dto.CreateDataDTO;
import com.partofa.dto.EditDataDTO;
import com.partofa.dto.RestMessageDTO;
import com.partofa.exception.BadRequestParametersException;
import com.partofa.repository.DataRepository;
import com.partofa.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public RestMessageDTO editData(EditDataDTO editDataDTO) {
        log.info(editDataDTO.toString());
        Data data = dataRepository.findOne(editDataDTO.getId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        data.setAddressWork(editDataDTO.getAddAdress());
        data.setCheckResult(editDataDTO.getAddResults());
        data.setComment(editDataDTO.getAddComent());
        data.setControlName(editDataDTO.getAddControll());
        data.setDeadlines(editDataDTO.getAddTerm());
        data.setGosWorkType(editDataDTO.getTypeD());
        data.setIpnPassport(editDataDTO.getIpn());
        data.setReactMeasure(editDataDTO.getAddZaxid());
        data.setRiscDesc(editDataDTO.getAddRskReason());
        try {
            data.setStartDate(simpleDateFormat.parse(editDataDTO.getStartDateP()));
        } catch (ParseException e) {
            log.warn(e.getMessage());
            throw new BadRequestParametersException("Дата у не вірному форматі");
        }
        data.setSubjectName(editDataDTO.getAddSubjectName());
        data.setUpdDate(new Date());
        log.info(data.toString());
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
    public RestMessageDTO createData(CreateDataDTO createDataDTO) throws BadRequestParametersException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            log.info(createDataDTO.toString());
            Data data = new Data();
            data.setAddressWork(createDataDTO.getAddAdress());
            data.setCheckResult(createDataDTO.getAddResults());
            data.setComment(createDataDTO.getAddComent());
            data.setControlName(createDataDTO.getAddControll());
            data.setDeadlines(createDataDTO.getAddTerm());
            data.setGosWorkType(createDataDTO.getTypeD());
            data.setIpnPassport(createDataDTO.getIpn());
            data.setReactMeasure(createDataDTO.getAddZaxid());
            data.setRiscDesc(createDataDTO.getAddRskReason());
            data.setStartDate(simpleDateFormat.parse(createDataDTO.getStartDateP()));
            data.setSubjectName(createDataDTO.getAddSubjectName());
            data.setAddDate(new Date());

            log.info(data.toString());
            dataRepository.save(data);
         }catch (ParseException e){
            log.warn(e.getMessage());
            throw new BadRequestParametersException("Дата у не вірному форматі");
        }
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
