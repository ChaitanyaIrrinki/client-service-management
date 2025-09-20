package com.java.postapi.repository;


import com.java.postapi.entity.ClientVendorMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ClientVendorsMappingEntityRepository extends JpaRepository<ClientVendorMappingEntity, String> {

}