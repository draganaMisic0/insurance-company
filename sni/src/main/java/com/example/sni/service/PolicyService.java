package com.example.sni.service;

import com.example.sni.entity.PolicyEntity;

import java.util.List;
import java.util.Optional;

public interface PolicyService {

    List<PolicyEntity> getAll();
    PolicyEntity save(PolicyEntity policy);
    Optional<PolicyEntity> getById(Integer id);
    void delete(Integer id);
}
