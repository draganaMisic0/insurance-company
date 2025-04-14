package com.example.sni.controller;

import com.example.sni.entity.InsuranceTypeEntity;
import com.example.sni.exception.ResourceNotFoundException;
import com.example.sni.model.InsuranceType;
import com.example.sni.service.InsuranceTypeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/insurance-types")
public class InsuranceTypeController {

    private InsuranceTypeService insuranceTypeService;

    @Autowired
    public InsuranceTypeController(InsuranceTypeService insuranceTypeService) {
        this.insuranceTypeService = insuranceTypeService;
    }

    @GetMapping("")
    public List<InsuranceType> getAll() {
        return insuranceTypeService.getAll().stream().map(it -> new InsuranceType(it.getId(), it.getName())).
                collect(Collectors.toList());

    }


    @PostMapping
    public InsuranceType create(@RequestBody InsuranceType it) {
        InsuranceTypeEntity newType = new InsuranceTypeEntity();
        newType.setName(it.getName());
        InsuranceTypeEntity saved = insuranceTypeService.save(newType);
        return new InsuranceType(saved.getId(), saved.getName());
    }
    @GetMapping("/{id}")
    public InsuranceType getById(@PathVariable Integer id) {
        Optional<InsuranceTypeEntity> insuranceTypeEntity = insuranceTypeService.getById(id);
        if (insuranceTypeEntity.isPresent()) {
            InsuranceTypeEntity it = insuranceTypeEntity.get();
            return new InsuranceType(it.getId(), it.getName());
        } else {
            throw new ResourceNotFoundException("InsuranceType not found with ID: " + id);
        }
    }

    // Update an existing insurance type
    @PutMapping("/{id}")
    public InsuranceType update(@PathVariable Integer id, @RequestBody InsuranceType it) {
        Optional<InsuranceTypeEntity> existingType = insuranceTypeService.getById(id);
        if (existingType.isPresent()) {
            InsuranceTypeEntity updatedType = existingType.get();
            updatedType.setName(it.getName());
            InsuranceTypeEntity saved = insuranceTypeService.save(updatedType);
            return new InsuranceType(saved.getId(), saved.getName());
        } else {
            throw new ResourceNotFoundException("InsuranceType not found with ID: " + id);
        }
    }

    // Delete an insurance type by ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Optional<InsuranceTypeEntity> existingType = insuranceTypeService.getById(id);
        if (existingType.isPresent()) {
            insuranceTypeService.delete(id);
        } else {
            throw new ResourceNotFoundException("InsuranceType not found with ID: " + id);
        }
    }
    public InsuranceTypeEntity update(Long id, InsuranceTypeEntity updatedInsuranceType) {
        Optional<InsuranceTypeEntity> existingInsuranceType = insuranceTypeService.findById(id);
        if (existingInsuranceType.isPresent()) {
            InsuranceTypeEntity existingType = existingInsuranceType.get();
            existingType.setName(updatedInsuranceType.getName());  // Update name or other fields
            return insuranceTypeService.save(existingType);
        } else {
            throw new ResourceNotFoundException("InsuranceType not found with ID: " + id);
        }
    }

    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(HttpServletRequest request) {

        return (CsrfToken)(request.getAttribute("_csrf"));
    }


}