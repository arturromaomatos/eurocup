package com.eurocup.app.repository;

import com.eurocup.app.domain.EuroOrderItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EuroOrderItem entity.
 */
@SuppressWarnings("unused")
public interface EuroOrderItemRepository extends JpaRepository<EuroOrderItem,Long> {

}
