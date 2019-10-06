package com.inori.cloud.providercart.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tbl_cart", schema = "inori_cloud_cart", catalog = "")
public class TblCart {
    private String uuid;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String userId;

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblCart tblCart = (TblCart) o;
        return Objects.equals(uuid, tblCart.uuid) &&
                Objects.equals(createdAt, tblCart.createdAt) &&
                Objects.equals(updatedAt, tblCart.updatedAt) &&
                Objects.equals(userId, tblCart.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, createdAt, updatedAt, userId);
    }
}
