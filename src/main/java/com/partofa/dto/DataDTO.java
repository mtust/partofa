package com.partofa.dto;

import com.partofa.domain.Data;
import com.partofa.domain.Region;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tust on 19.09.2016.
 */
@Slf4j
@lombok.Data
public class DataDTO {

    public DataDTO(Data data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        subjectName = data.getSubjectName();
        addressWork = data.getAddressWork();
        ipnPassport = data.getIpnPassport();
        gosWorkType = data.getGosWorkType();
        status = data.getStatus();
        if (data.getStartDate() != null) {
            startDate = simpleDateFormat.format(data.getStartDate());
        } else {
            startDate = null;
        }
        deadlines = data.getDeadlines();
        controlName = data.getControlName();
        id = data.getId();
        riscDesc = data.getRiscDesc();
        checkResult = data.getCheckResult();
        if (data.getAddDate() != null) {
            addDate = simpleDateFormat.format(data.getAddDate());
        } else {
            addDate = null;
        }
        if (data.getUpdDate() != null) {
            updDate = simpleDateFormat.format(data.getUpdDate());
        } else {
            updDate = null;
        }
        if (data.getDelDate() != null) {
            delDate = simpleDateFormat.format(data.getDelDate());
        } else {
            delDate = null;
        }
        reactMeasure = data.getReactMeasure();
        status = data.getStatus();
        comment = data.getComment();
        keyColumnRev = data.getKeyColumnRev();
        region = data.getRegion() == null ? "Усі регіони" : data.getRegion().getName();
    }

    private String subjectName;
    private String addressWork;
    private String ipnPassport;
    private String gosWorkType;
    private String status;
    private String startDate;
    private String deadlines;
    private String controlName;
    private Long id;
    private Integer chpId;
    private String riscDesc;
    private String checkResult;
    private String addDate;
    private String updDate;
    private String delDate;
    private String comment;
    private BigDecimal keyColumnRev;
    private String reactMeasure;
    private String region;


}
