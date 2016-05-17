package com.eurocup.app.web.rest.mapper;

import com.eurocup.app.domain.*;
import com.eurocup.app.web.rest.dto.EuroTicketDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity EuroTicket and its DTO EuroTicketDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EuroTicketMapper {

    EuroTicketDTO euroTicketToEuroTicketDTO(EuroTicket euroTicket);

    List<EuroTicketDTO> euroTicketsToEuroTicketDTOs(List<EuroTicket> euroTickets);

    @Mapping(target = "name", ignore = true)
    EuroTicket euroTicketDTOToEuroTicket(EuroTicketDTO euroTicketDTO);

    List<EuroTicket> euroTicketDTOsToEuroTickets(List<EuroTicketDTO> euroTicketDTOs);
}
