package com.java.postapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.postapi.entity.ClientVendorMappingEntity;
import com.java.postapi.entity.ClientVendorsEntityMapping;
import com.java.postapi.entity.FilingStatusEnumMapping;
import com.java.postapi.model.ClientVendors;
import com.java.postapi.repository.ClientVendorsEntityMappingRepository;
import com.java.postapi.repository.ClientVendorsMappingEntityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.UUID;
@Service
public class ClientVendorsService {
    @Autowired
    private final ObjectMapper objectMapper;
    private final ClientVendorsEntityMappingRepository clientVendorsEntityMappingRepository;
    private final ClientVendorsMappingEntityRepository  clientVendorsMappingEntityRepository;


    public ClientVendorsService(ObjectMapper objectMapper, ClientVendorsEntityMappingRepository clientVendorsEntityMappingRepository, ClientVendorsMappingEntityRepository clientVendorsMappingEntityRepository) {
        this.objectMapper = objectMapper;
        this.clientVendorsEntityMappingRepository = clientVendorsEntityMappingRepository;
        this.clientVendorsMappingEntityRepository = clientVendorsMappingEntityRepository;
    }

    /**
     * Creates a new vendor with a generated UUID and saves it to the database.
     * Sends an SQS message if the vendor contains a valid payload.
     *
     * @param vendor the vendor entity to create
     * @param org the organization ID
     * @param user the user performing the operation
     * @return the saved vendor entity
     */
    public ClientVendors createVendor(ClientVendors vendor, String org, String user, boolean isExistingVendor) throws JsonProcessingException {
        if(!isExistingVendor) {
            vendor.setId(UUID.randomUUID().toString());
            if (StringUtils.isBlank(vendor.getFilingStatus())) {
                vendor.setFilingStatus("Not_started");
            }
          clientVendorsEntityMappingRepository.save(clientVendorsToClientVendorsEntity(vendor));
        }
        clientVendorsMappingEntityRepository.save(clientVendorMappingModelToEntity(vendor));

        return vendor;
    }


    /**
     * Converts a {@link ClientVendors} model object to a {@link ClientVendorsEntityMapping} entity object for persistence.
     *
     * @param clientVendors The vendor DTO to convert.
     * @return The corresponding entity object.
     */
    public ClientVendorsEntityMapping clientVendorsToClientVendorsEntity(ClientVendors clientVendors){
        ClientVendorsEntityMapping clientVendorEntity = new ClientVendorsEntityMapping();
        clientVendorEntity.setId(clientVendors.getId());
        clientVendorEntity.setVendorName(clientVendors.getVendorName());
        clientVendorEntity.setEligibleFor1099(clientVendors.isEligibleFor1099());
        clientVendorEntity.setCity(clientVendors.getCity());
        clientVendorEntity.setState(clientVendors.getState());
        clientVendorEntity.setAddress(clientVendors.getAddress());
        clientVendorEntity.setZipCode(clientVendors.getZipCode());
        clientVendorEntity.setEinSsn(clientVendors.getEinSsn());
        clientVendorEntity.setEmail(clientVendors.getEmail());
        clientVendorEntity.setPhone(clientVendors.getPhone());
        return clientVendorEntity;
    }

    /**
     * Converts a {@link ClientVendors} model object to a {@link ClientVendorMappingEntity} entity object for persistence.
     * Maps vendor information, filing details, and document payload for database storage.
     *
     * @param model The ClientVendors model to convert
     * @return The corresponding ClientVendorMappingEntity object
     * @throws JsonProcessingException if the document payload cannot be serialized to JSON
     */
    public ClientVendorMappingEntity clientVendorMappingModelToEntity(ClientVendors model) throws JsonProcessingException {
        ClientVendorMappingEntity entity = new ClientVendorMappingEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setClientId(model.getClientId());
        entity.setVendorId(model.getId());
        entity.setYear(model.getYear() != 0 ? model.getYear() : Year.now().getValue());
        entity.setAmount(model.getAmount() != null ? model.getAmount() : BigDecimal.ZERO);
        entity.setFiling_type(model.getFilingType());
        entity.setRollForward(model.isRollForward());

        if (model.getFilingStatus() != null) {
            String filingStatus = model.getFilingStatus();
            if(filingStatus.equalsIgnoreCase("NOT STARTED")){
                entity.setFiling_status(FilingStatusEnumMapping.Not_started);
            }else if(filingStatus.equalsIgnoreCase("FILED")){
                entity.setFiling_status(FilingStatusEnumMapping.filed);
            }else if (filingStatus.equalsIgnoreCase("DRAFT")){
                entity.setFiling_status(FilingStatusEnumMapping.draft);
            }
        }
        if (model.getDocument() != null && !model.getDocument().isEmpty()) {
            entity.setFilePayload(objectMapper.writeValueAsString(model.getDocument()));
        } else {
            entity.setFilePayload("[]");
        }
        return entity;
    }

}
