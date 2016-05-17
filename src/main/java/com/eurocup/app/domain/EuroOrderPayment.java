package com.eurocup.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A EuroOrderPayment.
 */
@Entity
@Table(name = "euro_order_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EuroOrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private String year;

    @Column(name = "cvc")
    private String cvc;

    @Column(name = "payment_entity")
    private String paymentEntity;

    @Column(name = "payment_reference")
    private String paymentReference;

    @Column(name = "total_price", precision=10, scale=2)
    private BigDecimal totalPrice;

    @Column(name = "card_cust_name")
    private String cardCustName;

    @Column(name = "payment_date")
    private ZonedDateTime paymentDate;

    @Column(name = "iban")
    private String iban;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(String paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCardCustName() {
        return cardCustName;
    }

    public void setCardCustName(String cardCustName) {
        this.cardCustName = cardCustName;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EuroOrderPayment euroOrderPayment = (EuroOrderPayment) o;
        if(euroOrderPayment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, euroOrderPayment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EuroOrderPayment{" +
            "id=" + id +
            ", method='" + method + "'" +
            ", cardNumber='" + cardNumber + "'" +
            ", month='" + month + "'" +
            ", year='" + year + "'" +
            ", cvc='" + cvc + "'" +
            ", paymentEntity='" + paymentEntity + "'" +
            ", paymentReference='" + paymentReference + "'" +
            ", totalPrice='" + totalPrice + "'" +
            ", cardCustName='" + cardCustName + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", iban='" + iban + "'" +
            '}';
    }
}
