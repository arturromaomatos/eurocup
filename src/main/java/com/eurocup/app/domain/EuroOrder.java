package com.eurocup.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EuroOrder.
 */
@Entity
@Table(name = "euro_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EuroOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private ZonedDateTime orderDate;

    @Column(name = "total_price", precision=10, scale=2)
    private BigDecimal totalPrice;

    @NotNull
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @OneToMany(mappedBy = "euroOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EuroOrderItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private EuroOrderPayment payment;

    @ManyToOne
    private User user;

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

    public Set<EuroOrderItem> getItems() {
        return items;
    }

    public void setItems(Set<EuroOrderItem> euroOrderItems) {
        this.items = euroOrderItems;
    }

    public EuroOrderPayment getPayment() {
        return payment;
    }

    public void setPayment(EuroOrderPayment euroOrderPayment) {
        this.payment = euroOrderPayment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EuroOrder euroOrder = (EuroOrder) o;
        if(euroOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, euroOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EuroOrder{" +
            "id=" + id +
            ", orderDate='" + orderDate + "'" +
            ", totalPrice='" + totalPrice + "'" +
            ", paymentStatus='" + paymentStatus + "'" +
            '}';
    }
}
