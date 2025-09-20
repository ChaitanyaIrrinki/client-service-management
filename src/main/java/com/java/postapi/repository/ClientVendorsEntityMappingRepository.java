package com.java.postapi.repository;


import com.java.postapi.entity.ClientVendorsEntityMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link ClientVendorsEntityMapping} entities.
 * Provides methods for CRUD operations and custom query to fetch vendors by client ID.
 */
    public interface ClientVendorsEntityMappingRepository extends JpaRepository<ClientVendorsEntityMapping, String> {

}
