package com.example.sni.service;


import com.example.sni.entity.InsuranceTypeEntity;
import com.example.sni.repository.InsuranceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsuranceTypeServiceImpl implements InsuranceTypeService {

    private final InsuranceTypeRepository insuranceTypeRepository;

    @Autowired
    public InsuranceTypeServiceImpl(InsuranceTypeRepository insuranceTypeRepository) {
        this.insuranceTypeRepository = insuranceTypeRepository;
    }


    @Override
    public List<InsuranceTypeEntity> getAll() {
        return insuranceTypeRepository.findAll();
    }
    @Override
    public InsuranceTypeEntity save(InsuranceTypeEntity insuranceType) {
        return insuranceTypeRepository.save(insuranceType);
    }

    public Optional<InsuranceTypeEntity> getById(Integer id) {
        return insuranceTypeRepository.findById(id);
    }




    public void delete(Integer id) {
        insuranceTypeRepository.deleteById(id);
    }

    @Override
    public Optional<InsuranceTypeEntity> findById(Long id) {
        return Optional.empty();
    }
}
