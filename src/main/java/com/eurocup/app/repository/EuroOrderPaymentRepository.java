package com.eurocup.app.repository;

import com.eurocup.app.domain.EuroOrderPayment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EuroOrderPayment entity.
 */
@SuppressWarnings("unused")
public interface EuroOrderPaymentRepository extends JpaRepository<EuroOrderPayment,Long> {

}
