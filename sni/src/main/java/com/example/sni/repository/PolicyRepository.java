package com.example.sni.repository;

import com.example.sni.entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PolicyRepository extends JpaRepository<PolicyEntity, Integer> {
}
