package com.example.sni.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserHasPolicyIdEntity implements Serializable {
    private static final long serialVersionUID = -1353455632176303517L;
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "policy_id", nullable = false)
    private Integer policyId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserHasPolicyIdEntity entity = (UserHasPolicyIdEntity) o;
        return Objects.equals(this.policyId, entity.policyId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyId, userId);
    }

}