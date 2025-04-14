package com.example.sni.service;

import com.example.sni.entity.InsuranceTypeEntity;

import java.util.List;
import java.util.Optional;

public interface InsuranceTypeService {

    List<InsuranceTypeEntity> getAll();
    InsuranceTypeEntity save(InsuranceTypeEntity insuranceTypeEntity);

    Optional<InsuranceTypeEntity> getById(Integer id);

    void delete(Integer id);

    Optional<InsuranceTypeEntity> findById(Long id);
}
