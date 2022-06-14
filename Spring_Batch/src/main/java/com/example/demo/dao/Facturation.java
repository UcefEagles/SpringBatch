package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Facturation {

    @Id
    private Long id;
    private Long id_customer;
    private Date dateFacturation;
    @Transient
    private String strFactureDate;
    private double amount;
}
