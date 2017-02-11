package com.partofa.controller;

import com.partofa.domain.Data;
import com.partofa.dto.*;
import com.partofa.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by tust on 09.09.2016.
 */
@RestController
@Slf4j
public class HomeController {

	@Autowired
	DataService dataService;

	@RequestMapping(value = "private/home", method = RequestMethod.GET)
	public List<Data> homePage() {
		log.info("log1");
		return dataService.getAllData();
	}

	@RequestMapping(value = "private/home/actualData", method = RequestMethod.GET)
	public List<DataDTO> getNonDeletedData() {
		return dataService.getNonDeletedData();
	}

	@RequestMapping(value = "private/home/deletedData", method = RequestMethod.GET)
	public List<DataDTO> getDeletedData() {
		return dataService.getDeletedData();
	}

	@RequestMapping(value = { "private/page/private/edit", "private/edit" }, method = RequestMethod.POST)
	public RestMessageDTO editData(EditDataDTO editDataDTO) {
		log.info("edit put:");
		 log.info("createDataDTO" + editDataDTO.toString());
		// log.info("id: " + id);
		log.info(editDataDTO.toString());
		return dataService.editData(editDataDTO);
	}

	@RequestMapping(value = { "private/page/private/delete", "private/delete" }, method = RequestMethod.DELETE)
	public RestMessageDTO deleteData(@RequestParam(value = "id") Long dataId) {
		return dataService.deleteData(dataId);
	}

	@RequestMapping(value = { "private/page/private/add", "private/add" }, method = RequestMethod.POST)
	public RestMessageDTO createData(CreateDataDTO createDataDTO) {
		return dataService.createData(createDataDTO);
	}

	@RequestMapping(value = "revert", method = RequestMethod.POST)
	public RestMessageDTO revertData(@RequestParam(value = "id") Long id) {
		return dataService.revertData(id);
	}

	@RequestMapping(value = {
			"private/home/addDocument" }, headers = "content-type=multipart/form-data", method = RequestMethod.POST)
	public RestMessageDTO SetDocumentById(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "id") Long id) throws IOException {
		log.info("Inside controller SetDocument");
		Long size = file.getSize();
		log.info("I HAVE A NEW FILE WITH SiZE " + size + " Name " + file.getOriginalFilename()  + " id " + id);
		return dataService.setDocument(file, id);
	}

	@RequestMapping(value = "private/home/actualDocuments", method = RequestMethod.GET)
	public List<DocumentNameDTO> getDocuments() throws SQLException, IOException{
		log.info("Inside controller getDocuments");
		//log.info(dataService.getDocuments(1L).size() + " to string List");
		return dataService.getDocumentNames(1L);
	}

	@RequestMapping(value = "private/home/actualDocuments/{id}", method = RequestMethod.GET)
	public List<DocumentNameDTO> getDocumentsByDataId(@PathVariable  Long id)throws SQLException, IOException{
		log.info("Inside controller getDocuments: " + id);
		return dataService.getDocumentNames(id);
	}

	@RequestMapping(value =  "private/download/file/{id}", method = RequestMethod.GET)
	public DocumentDTO getDocumentForDownload(@PathVariable Long id) throws IOException, SQLException {
		log.info("Inside controller getDocument: " + id);
		return dataService.getDocument(id);
	}
	
	@RequestMapping(value = { "private/page/private/deleteDocument", "private/deleteDocument" }, method = RequestMethod.DELETE)
	public RestMessageDTO deleteDocuments(@RequestParam(value = "id") Long documentId) {
		log.info("HOME CONTROLLER HAVE DELETE DOCUMENT WITH ID " + documentId);
		return dataService.delDocument(documentId);
	}

    @RequestMapping(value= "private/home/import/file",headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    public RestMessageDTO importData(@RequestParam("file") MultipartFile excelFile) throws IOException {
        return dataService.importData(excelFile);
    }

    @RequestMapping(value = "getExample", method = RequestMethod.POST)
	@ResponseBody
	public String getExampleFile(HttpServletRequest request){
		log.info("getExample");
		log.info("base path: " + HomeController.class.getClassLoader().getResource("static/приклад.xlsx").getPath());
		// or this
		final DefaultResourceLoader loader = new DefaultResourceLoader();
	//	Resource resource = loader.getResource("classpath:static/images.png");
		try {
			//	File myFile = resource.getFile();
			URL resource = HomeController.class.getClassLoader().getResource("static/приклад.xlsx");

			 return new String(Base64.encodeBase64(IOUtils.toByteArray(resource)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
