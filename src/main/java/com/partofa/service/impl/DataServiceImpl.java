package com.partofa.service.impl;

import com.partofa.domain.Data;
import com.partofa.domain.Document;
import com.partofa.domain.Region;
import com.partofa.domain.User;
import com.partofa.dto.*;
import com.partofa.exception.BadRequestParametersException;
import com.partofa.exception.GeneralServiceException;
import com.partofa.repository.DataRepository;
import com.partofa.repository.DocumentRepository;
import com.partofa.repository.RegionRepository;
import com.partofa.repository.UserRepository;
import com.partofa.security.SecurityUtils;
import com.partofa.service.DataService;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.LobCreator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
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

	@Autowired
	DocumentRepository documentRepository;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

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
		if (!editDataDTO.getEditUserRegion().equals("all")) {
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
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
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
		} catch (ParseException e) {
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
		if (region != null) {
			dataRepository.findByDelDateIsNullAndRegion(region)
					.forEach(dataRegion -> dataDTOs.add(new DataDTO(dataRegion)));
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
		if (region != null) {
			dataRepository.findByDelDateIsNotNullAndRegion(region)
					.forEach(dataRegion -> dataDTOs.add(new DataDTO(dataRegion)));
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

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	UserRepository userRepository;

	@Transactional
	public User getLoginUser() {
		String userLogin = SecurityUtils.getCurrentUserLogin();
		return userRepository.findByEmail(userLogin);

	}

	@Transactional
	public RestMessageDTO setDocument(MultipartFile file, Long idData) throws IOException {
		log.info("inside DataServiceIMPL setDocument");
		User user = getLoginUser();
		log.info("user: " + user);
		Session session = sessionFactory.getCurrentSession();
		Data data = dataRepository.findOne(idData);
		LobCreator lobCreator = Hibernate.getLobCreator(session);
		Blob blob = lobCreator.createBlob(file.getBytes());
		Document document = new Document();
		document.setFile(blob);
		document.setName(file.getOriginalFilename());
		documentRepository.save(document);
		List<Document> list = data.getDocuments();
		if(list == null){
			list = new ArrayList<>();
		}
		list.add(document);
		data.setDocuments(list);
		dataRepository.save(data);

		return new RestMessageDTO("Success", true);

	}

	@Override
	@Transactional
	public DocumentDTO getDocument(Long id) throws SQLException, IOException {
		Document document = documentRepository.findOne(id);
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setId(document.getId());
		documentDTO.setName(document.getName());
		InputStream inputStream = document.getFile().getBinaryStream();
		documentDTO.setDocument(IOUtils.toByteArray(inputStream));
		return documentDTO;
	}

	@Transactional
	@Override
	public List<DocumentDTO> getDocuments(Long idData) throws SQLException, IOException {
		Data data = dataRepository.findOne(idData);
		List<Document> list = data.getDocuments();
		List<DocumentDTO> listDTO = new ArrayList<>();
		for (Document document : list) {
			DocumentDTO documentDTO = new DocumentDTO();
			documentDTO.setId(document.getId());
			documentDTO.setName(document.getName());
			InputStream inputStream = document.getFile().getBinaryStream();
			documentDTO.setDocument(IOUtils.toByteArray(inputStream));
			
			listDTO.add(documentDTO);
		}
		return listDTO;
	}

	@Override
	@Transactional
	public List<DocumentNameDTO> getDocumentNames(Long idData) {
		Data data = dataRepository.findOne(idData);
		List<Document> list = data.getDocuments();
		List<DocumentNameDTO> listDTO = new ArrayList<>();
		for (Document document : list) {
			DocumentNameDTO documentNameDTO = new DocumentNameDTO();
			documentNameDTO.setId(document.getId());
			documentNameDTO.setName(document.getName());

			listDTO.add(documentNameDTO);
		}
		return listDTO;
	}

	@Transactional
	public RestMessageDTO delDocument(Long idDocument) {
		documentRepository.delete(idDocument);
		return new RestMessageDTO("Success", true);

	}

    @Override
    @Transactional
    public RestMessageDTO importData(MultipartFile excelFile) throws IOException {

        try (InputStream file = excelFile.getInputStream()){

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if(rowIterator.hasNext()){
                rowIterator.next();
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Data data = new Data();
                data.setSubjectName(row.getCell(1).toString());
                data.setRegion(row.getCell(2).toString().equalsIgnoreCase("Усі регіони")
                ? null : regionRepository.findByName(row.getCell(2).toString()));
                data.setAddressWork(row.getCell(3).toString());
                data.setIpnPassport(row.getCell(4).toString());
                data.setGosWorkType(row.getCell(5).toString());
              //  data.setRiscDesc(row.getCell(6).toString());
                System.out.println(data.toString());
                if(row.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(row.getCell(7))) {
                    data.setStartDate(row.getCell(7).getDateCellValue());
                } else {
                    data.setStartDate(simpleDateFormat.parse(row.getCell(7).toString()));
                }
                data.setDeadlines(row.getCell(8).toString());
                data.setRiscDesc(row.getCell(9).toString());
                data.setCheckResult(row.getCell(10).toString());
                data.setReactMeasure(row.getCell(11).toString());
                data.setControlName(row.getCell(12).toString());
                if(row.getCell(13).getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(row.getCell(13))) {
                    data.setAddDate(row.getCell(13).getDateCellValue());
                } else {
                    try {
                        data.setAddDate(simpleDateFormat.parse(row.getCell(13).toString()));
                    } catch (ParseException e) {
                        data.setAddDate(new Date());
                        log.warn(e.getMessage() + "\n" + e.getStackTrace().toString());
                    }
                }
                if(row.getCell(14).getCellType() == Cell.CELL_TYPE_NUMERIC &&  DateUtil.isCellDateFormatted(row.getCell(14))) {
                    data.setUpdDate(row.getCell(14).getDateCellValue());
                } else {
                    try {
                        data.setUpdDate(simpleDateFormat.parse(row.getCell(14).toString()));
                    } catch (ParseException e) {
                        log.warn(e.getMessage() + "\n" + e.getStackTrace().toString());
                    }
                }
                data.setComment(row.getCell(15).toString());


//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    switch (cell.getCellType()) {
//                        case Cell.CELL_TYPE_NUMERIC:
//                            System.out.print(cell.getNumericCellValue() + "\t");
//                            break;
//                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cell.getStringCellValue() + "\t");
//                            break;
//                    }
//                }
//                System.out.println();
                System.out.println(data.toString());
                dataRepository.save(data);
            }
        } catch (OLE2NotOfficeXmlFileException e ){
            log.error(e.getMessage());
            e.printStackTrace();
            new GeneralServiceException("Неможливо імпортувати файл, використовуйте новішу версію Microsoft Excel (2003 або новіше)");
        } catch (ParseException e){
            log.error(e.getMessage());
            e.printStackTrace();
            new GeneralServiceException("Невірний формат дат (використовуйте формат дд.мм.рррр)");
        } catch (NotOfficeXmlFileException e){
			log.error(e.getMessage());
			e.printStackTrace();
			new GeneralServiceException("Неможливо імпортувати файл, файл не являється документом Microsoft excel");
		}

            return new RestMessageDTO("Success", true);

    }
}
