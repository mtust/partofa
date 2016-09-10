package com.partofa.domain;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by tust on 09.09.2016.
 */


@lombok.Data
@Slf4j
@Entity
@Table(name = "data")
public class Data {


    @Column(name = "sub_name")
    private String subjectName;
    @Column(name = "address_work")
    private String addressWork;
    @Column(name = "ipn_passport")
    private String ipnPassport;
    @Column(name = "gos_work_type")
    private String gosWorkType;
    @Column(name = "status")
    private String status;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "deadlines")
    private String deadlines;
    @Column(name = "control_name")
    private String controlName;
    @Id
    private Long id;
    @Column(name = "chp_id")
    private Integer chpId;
    @Column(name = "risk_desc")
    private String riscDesc;
    @Column(name = "check_result")
    private String checkResult;
    @Column(name = "add_date")
    private Timestamp addDate;
    @Column(name = "upd_date")
    private Timestamp updDate;
    @Column(name = "del_date")
    private Timestamp delDate;
    @Column(name = "commentary")
    private String comment;
    @Column(name = "key_column_rev")
    private BigDecimal keyColumnRev;
    @Column(name = "react_measure")
    private String reactMeasure;


}
