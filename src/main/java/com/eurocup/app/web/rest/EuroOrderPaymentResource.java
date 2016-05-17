package com.eurocup.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eurocup.app.domain.EuroOrderPayment;
import com.eurocup.app.repository.EuroOrderPaymentRepository;
import com.eurocup.app.web.rest.util.HeaderUtil;
import com.eurocup.app.web.rest.dto.EuroOrderPaymentDTO;
import com.eurocup.app.web.rest.mapper.EuroOrderPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing EuroOrderPayment.
 */
@RestController
@RequestMapping("/api")
public class EuroOrderPaymentResource {

    private final Logger log = LoggerFactory.getLogger(EuroOrderPaymentResource.class);
        
    @Inject
    private EuroOrderPaymentRepository euroOrderPaymentRepository;
    
    @Inject
    private EuroOrderPaymentMapper euroOrderPaymentMapper;
    
    /**
     * POST  /euro-order-payments : Create a new euroOrderPayment.
     *
     * @param euroOrderPaymentDTO the euroOrderPaymentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new euroOrderPaymentDTO, or with status 400 (Bad Request) if the euroOrderPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/euro-order-payments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderPaymentDTO> createEuroOrderPayment(@Valid @RequestBody EuroOrderPaymentDTO euroOrderPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save EuroOrderPayment : {}", euroOrderPaymentDTO);
        if (euroOrderPaymentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("euroOrderPayment", "idexists", "A new euroOrderPayment cannot already have an ID")).body(null);
        }
        EuroOrderPayment euroOrderPayment = euroOrderPaymentMapper.euroOrderPaymentDTOToEuroOrderPayment(euroOrderPaymentDTO);
        euroOrderPayment = euroOrderPaymentRepository.save(euroOrderPayment);
        EuroOrderPaymentDTO result = euroOrderPaymentMapper.euroOrderPaymentToEuroOrderPaymentDTO(euroOrderPayment);
        return ResponseEntity.created(new URI("/api/euro-order-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("euroOrderPayment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /euro-order-payments : Updates an existing euroOrderPayment.
     *
     * @param euroOrderPaymentDTO the euroOrderPaymentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated euroOrderPaymentDTO,
     * or with status 400 (Bad Request) if the euroOrderPaymentDTO is not valid,
     * or with status 500 (Internal Server Error) if the euroOrderPaymentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/euro-order-payments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderPaymentDTO> updateEuroOrderPayment(@Valid @RequestBody EuroOrderPaymentDTO euroOrderPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update EuroOrderPayment : {}", euroOrderPaymentDTO);
        if (euroOrderPaymentDTO.getId() == null) {
            return createEuroOrderPayment(euroOrderPaymentDTO);
        }
        EuroOrderPayment euroOrderPayment = euroOrderPaymentMapper.euroOrderPaymentDTOToEuroOrderPayment(euroOrderPaymentDTO);
        euroOrderPayment = euroOrderPaymentRepository.save(euroOrderPayment);
        EuroOrderPaymentDTO result = euroOrderPaymentMapper.euroOrderPaymentToEuroOrderPaymentDTO(euroOrderPayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("euroOrderPayment", euroOrderPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /euro-order-payments : get all the euroOrderPayments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of euroOrderPayments in body
     */
    @RequestMapping(value = "/euro-order-payments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<EuroOrderPaymentDTO> getAllEuroOrderPayments() {
        log.debug("REST request to get all EuroOrderPayments");
        List<EuroOrderPayment> euroOrderPayments = euroOrderPaymentRepository.findAll();
        return euroOrderPaymentMapper.euroOrderPaymentsToEuroOrderPaymentDTOs(euroOrderPayments);
    }

    /**
     * GET  /euro-order-payments/:id : get the "id" euroOrderPayment.
     *
     * @param id the id of the euroOrderPaymentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the euroOrderPaymentDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/euro-order-payments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderPaymentDTO> getEuroOrderPayment(@PathVariable Long id) {
        log.debug("REST request to get EuroOrderPayment : {}", id);
        EuroOrderPayment euroOrderPayment = euroOrderPaymentRepository.findOne(id);
        EuroOrderPaymentDTO euroOrderPaymentDTO = euroOrderPaymentMapper.euroOrderPaymentToEuroOrderPaymentDTO(euroOrderPayment);
        return Optional.ofNullable(euroOrderPaymentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /euro-order-payments/:id : delete the "id" euroOrderPayment.
     *
     * @param id the id of the euroOrderPaymentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/euro-order-payments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEuroOrderPayment(@PathVariable Long id) {
        log.debug("REST request to delete EuroOrderPayment : {}", id);
        euroOrderPaymentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("euroOrderPayment", id.toString())).build();
    }

}
