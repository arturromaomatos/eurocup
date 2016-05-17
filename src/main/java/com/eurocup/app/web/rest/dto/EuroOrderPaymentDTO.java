package com.eurocup.app.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A DTO for the EuroOrderPayment entity.
 */
public class EuroOrderPaymentDTO implements Serializable {

    private Long id;

    @NotNull
    private String method;


    private String cardNumber;


    private String month;


    private String year;


    private String cvc;


    private String paymentEntity;


    private String paymentReference;


    private BigDecimal totalPrice;


    private String cardCustName;


    private ZonedDateTime paymentDate;


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

        EuroOrderPaymentDTO euroOrderPaymentDTO = (EuroOrderPaymentDTO) o;

        if ( ! Objects.equals(id, euroOrderPaymentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EuroOrderPaymentDTO{" +
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
