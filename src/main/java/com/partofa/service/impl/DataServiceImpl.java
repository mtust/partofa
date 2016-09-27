package com.partofa.service.impl;

import com.partofa.domain.Data;
import com.partofa.domain.Region;
import com.partofa.domain.User;
import com.partofa.dto.CreateDataDTO;
import com.partofa.dto.DataDTO;
import com.partofa.dto.EditDataDTO;
import com.partofa.dto.RestMessageDTO;
import com.partofa.exception.BadRequestParametersException;
import com.partofa.repository.DataRepository;
import com.partofa.repository.RegionRepository;
import com.partofa.service.DataService;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    UserService userService;

    @Autowired
    RegionRepository regionRepository;

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
        Region region = null;
        if(!editDataDTO.getEditUserRegion().equals("all")) {
            region = regionRepository.findOne(Long.parseLong(editDataDTO.getEditUserRegion()));
        }
        data.setRegion(region);
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
            Region region = null;
            if(!createDataDTO.getAddUserRegion().equals("all")) {
                region = regionRepository.findOne(Long.parseLong(createDataDTO.getAddUserRegion()));
            }
            data.setRegion(region);

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
    public List<DataDTO> getNonDeletedData() {
        List<DataDTO> dataDTOs = new ArrayList<>();
        User user = userService.getLoginUser();
        Region region = user.getRegion();
        if(region != null){
            dataRepository.findByDelDateIsNullAndRegion(region).forEach(dataRegion -> dataDTOs.add(new DataDTO(dataRegion)));
        } else {
            dataRepository.findByDelDateIsNull().forEach(data -> dataDTOs.add(new DataDTO(data)));
        }
        return dataDTOs;
    }

    @Transactional
    @Override
    public List<DataDTO> getDeletedData() {
        List<DataDTO> dataDTOs = new ArrayList<>();
        User user = userService.getLoginUser();
        Region region = user.getRegion();
        if(region != null){
            dataRepository.findByDelDateIsNotNullAndRegion(region).forEach(dataRegion -> dataDTOs.add(new DataDTO(dataRegion)));
        } else {
            dataRepository.findByDelDateIsNotNull().forEach(data -> dataDTOs.add(new DataDTO(data)));
        }
        return dataDTOs;
    }


    @Override
    public RestMessageDTO revertData(Long id) {

        Data data = dataRepository.findOne(id);
        data.setDelDate(null);
        dataRepository.save(data);

        return new RestMessageDTO("Success", true);
    }

    @Override
    public RestMessageDTO importData(String id, MultipartFile excelFile) throws IOException {

        InputStream file = excelFile.getInputStream();
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                //    switch ()
            }
        }
        return new RestMessageDTO("Success", true);
    }
}
