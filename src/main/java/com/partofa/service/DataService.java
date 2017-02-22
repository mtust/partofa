package com.partofa.service;

import com.partofa.domain.Data;
import com.partofa.dto.*;
import com.partofa.exception.BadRequestParametersException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
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

    List<DataDTO> getNonDeletedData();

    List<DataDTO> getDeletedData();

    RestMessageDTO revertData(Long id);

    RestMessageDTO importData(MultipartFile excelFile) throws IOException;
    RestMessageDTO setDocument(MultipartFile file, Long idData) throws IOException;
    
    List<DocumentDTO> getDocuments(Long idData) throws SQLException, IOException;

    List<DocumentNameDTO> getDocumentNames(Long idData);
    
    RestMessageDTO delDocument(Long idDocument);

    DocumentDTO getDocument(Long id)  throws SQLException, IOException;

    List<DataDTO> getDataById(Long id);
}
