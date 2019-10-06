package com.inori.cloud.providercart.pojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tbl_order", schema = "inori_cloud_cart", catalog = "")
public class TblOrder {
    private String uuid;
    private String orderName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer count;
    private String shopName;
    private String cartId;
    private String commodityId;

    @Id
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "order_name")
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
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
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Basic
    @Column(name = "cart_id")
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Basic
    @Column(name = "commodity_id")
    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblOrder tblOrder = (TblOrder) o;
        return Objects.equals(uuid, tblOrder.uuid) &&
                Objects.equals(orderName, tblOrder.orderName) &&
                Objects.equals(createdAt, tblOrder.createdAt) &&
                Objects.equals(updatedAt, tblOrder.updatedAt) &&
                Objects.equals(count, tblOrder.count) &&
                Objects.equals(shopName, tblOrder.shopName) &&
                Objects.equals(cartId, tblOrder.cartId) &&
                Objects.equals(commodityId, tblOrder.commodityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, orderName, createdAt, updatedAt, count, shopName, cartId, commodityId);
    }
}
