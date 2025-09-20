package com.java.postapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.java.postapi.model.ClientVendors;
import com.java.postapi.service.ClientVendorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orgs/{orgId}/users/{userId}/1099clients/{clientId}/vendors")
public class ClientVendorsController {

    @Autowired
    private ClientVendorsService clientVendorsService;

    /**
     * Creates a new vendor associated with a client.
     *
     * @param vendor the vendor entity to create
     * @param orgId the organization ID from the path
     * @param userId the user ID from the path
     * @param isExistingVendor the isExisting vendor from the query param
     * @return the created vendor entity
     */

    @PostMapping
    public ResponseEntity<ClientVendors> createNewVendor(
            @RequestBody ClientVendors vendor,
            @PathVariable("userId") String userId,
            @PathVariable ("orgId") String orgId,
            @RequestParam(value = "isExistingVendor", required = false, defaultValue = "false") boolean isExistingVendor) throws JsonProcessingException {
        ClientVendors savedVendor = clientVendorsService.createVendor(vendor, orgId, userId,isExistingVendor);
        return ResponseEntity.ok(savedVendor);
    }
}
