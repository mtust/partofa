package com.partofa.service;

import com.partofa.domain.Data;
import com.partofa.dto.CreateDataDTO;
import com.partofa.dto.EditDataDTO;
import com.partofa.dto.RestMessageDTO;
import com.partofa.exception.BadRequestParametersException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Service
public interface DataService {

    List<Data> getAllData();

    RestMessageDTO editData(EditDataDTO editDataDTO);

    RestMessageDTO deleteData(Long dataId);

    RestMessageDTO createData(CreateDataDTO createDataDTO) throws BadRequestParametersException;

    List<Data> getNonDeletedData();

    List<Data> getDeletedData();
}
