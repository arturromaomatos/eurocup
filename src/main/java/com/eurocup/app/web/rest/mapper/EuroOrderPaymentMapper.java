package com.eurocup.app.web.rest.mapper;

import com.eurocup.app.domain.*;
import com.eurocup.app.web.rest.dto.EuroOrderPaymentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity EuroOrderPayment and its DTO EuroOrderPaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EuroOrderPaymentMapper {

    EuroOrderPaymentDTO euroOrderPaymentToEuroOrderPaymentDTO(EuroOrderPayment euroOrderPayment);

    List<EuroOrderPaymentDTO> euroOrderPaymentsToEuroOrderPaymentDTOs(List<EuroOrderPayment> euroOrderPayments);

    EuroOrderPayment euroOrderPaymentDTOToEuroOrderPayment(EuroOrderPaymentDTO euroOrderPaymentDTO);

    List<EuroOrderPayment> euroOrderPaymentDTOsToEuroOrderPayments(List<EuroOrderPaymentDTO> euroOrderPaymentDTOs);
}
