package com.example.sni.controller;

import com.example.sni.entity.PolicyEntity;
import com.example.sni.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController

public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/{role}/policy-list")
    public ResponseEntity<List<PolicyEntity>> getAllPolicies() {
        return ResponseEntity.ok(policyService.getAll());
    }

}
