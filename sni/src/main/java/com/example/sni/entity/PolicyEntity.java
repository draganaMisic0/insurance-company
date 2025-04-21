package com.example.sni.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "policy")
public class PolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;



    @Column(name = "pdf_url", nullable = false, length = 200)
    private String pdfUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance_type_id", nullable = false)
    private InsuranceTypeEntity insuranceType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public InsuranceTypeEntity getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceTypeEntity insuranceType) {
        this.insuranceType = insuranceType;
    }

}