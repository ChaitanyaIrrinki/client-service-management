package com.java.postapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "client_vendor_mapping")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendorMappingEntity {

    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "vendor_id", nullable = false)
    private String vendorId;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "filing_status")
    private FilingStatusEnumMapping filing_status;

    @Column(name = "filing_type")
    private String filing_type;

    @Column(name = "file_payload")
    private String filePayload;

    @Column(name = "roll_forward")
    private boolean rollForward;

}