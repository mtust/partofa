package com.partofa.dto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@lombok.Data
public class DocumentDTO {

    Long id;
    String name;
    byte [] document;



    public DocumentDTO(Long id, String name, byte [] document) {
        super();
        this.id = id;
        this.name = name;
        this.document = document;
    }

    public DocumentDTO() {
        super();
    }



}