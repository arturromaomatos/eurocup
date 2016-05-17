package com.eurocup.app.web.rest.mapper;

import com.eurocup.app.domain.*;
import com.eurocup.app.web.rest.dto.EuroOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity EuroOrder and its DTO EuroOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface EuroOrderMapper {

    @Mapping(source = "payment.id", target = "paymentId")
    @Mapping(source = "user.id", target = "userId")
    EuroOrderDTO euroOrderToEuroOrderDTO(EuroOrder euroOrder);

    List<EuroOrderDTO> euroOrdersToEuroOrderDTOs(List<EuroOrder> euroOrders);

    @Mapping(target = "items", ignore = true)
    @Mapping(source = "paymentId", target = "payment")
    @Mapping(source = "userId", target = "user")
    EuroOrder euroOrderDTOToEuroOrder(EuroOrderDTO euroOrderDTO);

    List<EuroOrder> euroOrderDTOsToEuroOrders(List<EuroOrderDTO> euroOrderDTOs);

    default EuroOrderPayment euroOrderPaymentFromId(Long id) {
        if (id == null) {
            return null;
        }
        EuroOrderPayment euroOrderPayment = new EuroOrderPayment();
        euroOrderPayment.setId(id);
        return euroOrderPayment;
    }
}
