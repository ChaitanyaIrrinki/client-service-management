package com.java.postapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ClientVendors {

    private String id;

    private String vendorName;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String einSsn;

    private boolean eligibleFor1099;

    private List<Document> document;

    private String clientId;

    private int year;

    private BigDecimal amount;

    private String filingStatus;

    private String filingType;

    private boolean rollForward;
}
