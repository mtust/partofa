package com.partofa.service;

import com.partofa.domain.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tust on 10.09.2016.
 */
@Service
public interface DataService {

    List<Data> getAllData();

}
