package com.java.postapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "client_vendors")
@Data
public class ClientVendorsEntityMapping {

    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "ein_ssn")
    private String einSsn;

    @Column(name = "is_1099_eligible")
    private boolean eligibleFor1099;
}