package com.example.sni.repository;

import com.example.sni.entity.InsuranceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceTypeEntity, Integer> {
}
