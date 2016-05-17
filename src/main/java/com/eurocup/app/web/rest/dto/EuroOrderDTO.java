package com.eurocup.app.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the EuroOrder entity.
 */
public class EuroOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime orderDate;


    private BigDecimal totalPrice;


    @NotNull
    private String paymentStatus;


    private Long userId;
    
    private Long paymentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long euroOrderPaymentId) {
        this.paymentId = euroOrderPaymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EuroOrderDTO euroOrderDTO = (EuroOrderDTO) o;

        if ( ! Objects.equals(id, euroOrderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EuroOrderDTO{" +
            "id=" + id +
            ", orderDate='" + orderDate + "'" +
            ", totalPrice='" + totalPrice + "'" +
            ", paymentStatus='" + paymentStatus + "'" +
            '}';
    }
}
