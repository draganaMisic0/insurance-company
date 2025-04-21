package com.example.sni.service;


import com.example.sni.entity.PolicyEntity;
import com.example.sni.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;

    @Autowired
    public PolicyServiceImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public List<PolicyEntity> getAll() {
        return policyRepository.findAll();
    }

    @Override
    public PolicyEntity save(PolicyEntity policy) {
        return policyRepository.save(policy);
    }

    @Override
    public Optional<PolicyEntity> getById(Integer id) {
        return policyRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        policyRepository.deleteById(id);
    }
}
