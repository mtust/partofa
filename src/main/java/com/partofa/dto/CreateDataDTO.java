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

    private String subjectName;
    private String addressWork;
    private String ipnPassport;
    private String gosWorkType;
    private String status;
    private Date startDate;
    private String deadlines;
    private String controlName;
    private Integer chpId;
    private String riscDesc;
    private String checkResult;
    private Date addDate;
    private String comment;
    private BigDecimal keyColumnRev;
    private String reactMeasure;

}
