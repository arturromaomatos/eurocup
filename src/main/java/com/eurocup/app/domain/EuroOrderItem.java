package com.eurocup.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A EuroOrderItem.
 */
@Entity
@Table(name = "euro_order_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EuroOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @NotNull
    @Column(name = "total_price", precision=10, scale=2, nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    private EuroOrder order;

    @OneToOne
    @JoinColumn(unique = true)
    private EuroTicket ticket;

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

    public EuroOrder getOrder() {
        return order;
    }

    public void setOrder(EuroOrder euroOrder) {
        this.order = euroOrder;
    }

    public EuroTicket getTicket() {
        return ticket;
    }

    public void setTicket(EuroTicket euroTicket) {
        this.ticket = euroTicket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EuroOrderItem euroOrderItem = (EuroOrderItem) o;
        if(euroOrderItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, euroOrderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EuroOrderItem{" +
            "id=" + id +
            ", quantity='" + quantity + "'" +
            ", totalPrice='" + totalPrice + "'" +
            '}';
    }
}
