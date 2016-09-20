package com.partofa.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tust on 18.09.2016.
 */
@Data
public class CreateDataDTO {

    private String addSubjectName;
    private String addAdress;
    private String ipn;
    private String typeD;
    private String addRisk;
    private String startDateP;
    private String addTerm;
    private String addControll;
    private String addRskReason;
    private String addResults;
    private String addComent;
    private String addZaxid;
    private String addUserRegion;

}
