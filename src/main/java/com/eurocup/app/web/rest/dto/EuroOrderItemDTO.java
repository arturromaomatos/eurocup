package com.eurocup.app.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the EuroOrderItem entity.
 */
public class EuroOrderItemDTO implements Serializable {

    private Long id;

    @NotNull
    private Long quantity;


    @NotNull
    private BigDecimal totalPrice;


    private Long euroOrderId;
    
    private Long ticketId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getEuroOrderId() {
        return euroOrderId;
    }

    public void setEuroOrderId(Long euroOrderId) {
        this.euroOrderId = euroOrderId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long euroTicketId) {
        this.ticketId = euroTicketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EuroOrderItemDTO euroOrderItemDTO = (EuroOrderItemDTO) o;

        if ( ! Objects.equals(id, euroOrderItemDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EuroOrderItemDTO{" +
            "id=" + id +
            ", quantity='" + quantity + "'" +
            ", totalPrice='" + totalPrice + "'" +
            '}';
    }
}
