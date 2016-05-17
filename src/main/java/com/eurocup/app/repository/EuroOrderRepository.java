package com.eurocup.app.repository;

import com.eurocup.app.domain.EuroOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EuroOrder entity.
 */
@SuppressWarnings("unused")
public interface EuroOrderRepository extends JpaRepository<EuroOrder,Long> {

    @Query("select euroOrder from EuroOrder euroOrder where euroOrder.user.login = ?#{principal.username}")
    List<EuroOrder> findByUserIsCurrentUser();

}
