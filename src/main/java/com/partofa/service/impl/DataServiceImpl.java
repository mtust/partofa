package com.partofa.service.impl;

import com.partofa.domain.Data;
import com.partofa.domain.Document;
import com.partofa.domain.Region;
import com.partofa.domain.User;
import com.partofa.dto.CreateDataDTO;
import com.partofa.dto.DataDTO;
import com.partofa.dto.DocumentDTO;
import com.partofa.dto.EditDataDTO;
import com.partofa.dto.RestMessageDTO;
import com.partofa.exception.BadRequestParametersException;
import com.partofa.repository.DataRepository;
import com.partofa.repository.DocumentRepository;
import com.partofa.repository.RegionRepository;
import com.partofa.repository.UserRepository;
import com.partofa.security.SecurityUtils;
import com.partofa.service.DataService;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
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

	@Autowired
	UserService userService;

	@Autowired
	RegionRepository regionRepository;

	@Autowired
	DocumentRepository documentRepository;

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
			if (!createDataDTO.getAddUserRegion().equals("all")) {
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
		list.add(document);
		data.setDocuments(list);
		dataRepository.save(data);

		return new RestMessageDTO("Success", true);

	}

	@Transactional
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

	@Transactional
	public RestMessageDTO delDocument(Long idDocument) {
		documentRepository.delete(idDocument);
		return new RestMessageDTO("Success", true);

	}
}
