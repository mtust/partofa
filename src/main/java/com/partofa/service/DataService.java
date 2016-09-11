package com.partofa.service;

import com.partofa.domain.Data;
import com.partofa.dto.RestMessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Service
public interface DataService {

    List<Data> getAllData();

    RestMessageDTO editData(Data data);

    RestMessageDTO deleteData(Long dataId);

    RestMessageDTO createData(Data data);

    List<Data> getNonDeletedData();

    List<Data> getDeletedData();
}
