package com.eurocup.app.repository;

import com.eurocup.app.domain.EuroTicket;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EuroTicket entity.
 */
@SuppressWarnings("unused")
public interface EuroTicketRepository extends JpaRepository<EuroTicket,Long> {

}
