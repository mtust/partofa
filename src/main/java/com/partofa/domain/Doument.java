package com.partofa.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by tust on 19.09.2016.
 */
@Entity
@Table(name = "file")
@lombok.Data
public class Doument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Blob file;
}
