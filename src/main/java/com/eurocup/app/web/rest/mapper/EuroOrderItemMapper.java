package com.eurocup.app.web.rest.mapper;

import com.eurocup.app.domain.*;
import com.eurocup.app.web.rest.dto.EuroOrderItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity EuroOrderItem and its DTO EuroOrderItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EuroOrderItemMapper {

    @Mapping(source = "euroOrder.id", target = "euroOrderId")
    @Mapping(source = "euroOrder.id", target = "euroOrderId")
    @Mapping(source = "ticket.id", target = "ticketId")
    EuroOrderItemDTO euroOrderItemToEuroOrderItemDTO(EuroOrderItem euroOrderItem);

    List<EuroOrderItemDTO> euroOrderItemsToEuroOrderItemDTOs(List<EuroOrderItem> euroOrderItems);

    @Mapping(source = "euroOrderId", target = "euroOrder")
    @Mapping(source = "euroOrderId", target = "euroOrder")
    @Mapping(source = "ticketId", target = "ticket")
    EuroOrderItem euroOrderItemDTOToEuroOrderItem(EuroOrderItemDTO euroOrderItemDTO);

    List<EuroOrderItem> euroOrderItemDTOsToEuroOrderItems(List<EuroOrderItemDTO> euroOrderItemDTOs);

    default EuroOrder euroOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        EuroOrder euroOrder = new EuroOrder();
        euroOrder.setId(id);
        return euroOrder;
    }

    default EuroTicket euroTicketFromId(Long id) {
        if (id == null) {
            return null;
        }
        EuroTicket euroTicket = new EuroTicket();
        euroTicket.setId(id);
        return euroTicket;
    }
}
