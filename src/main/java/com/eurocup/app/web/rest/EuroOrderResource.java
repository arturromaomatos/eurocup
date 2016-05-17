package com.eurocup.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eurocup.app.domain.EuroOrder;
import com.eurocup.app.repository.EuroOrderRepository;
import com.eurocup.app.web.rest.util.HeaderUtil;
import com.eurocup.app.web.rest.dto.EuroOrderDTO;
import com.eurocup.app.web.rest.mapper.EuroOrderMapper;
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
 * REST controller for managing EuroOrder.
 */
@RestController
@RequestMapping("/api")
public class EuroOrderResource {

    private final Logger log = LoggerFactory.getLogger(EuroOrderResource.class);
        
    @Inject
    private EuroOrderRepository euroOrderRepository;
    
    @Inject
    private EuroOrderMapper euroOrderMapper;
    
    /**
     * POST  /euro-orders : Create a new euroOrder.
     *
     * @param euroOrderDTO the euroOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new euroOrderDTO, or with status 400 (Bad Request) if the euroOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/euro-orders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderDTO> createEuroOrder(@Valid @RequestBody EuroOrderDTO euroOrderDTO) throws URISyntaxException {
        log.debug("REST request to save EuroOrder : {}", euroOrderDTO);
        if (euroOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("euroOrder", "idexists", "A new euroOrder cannot already have an ID")).body(null);
        }
        EuroOrder euroOrder = euroOrderMapper.euroOrderDTOToEuroOrder(euroOrderDTO);
        euroOrder = euroOrderRepository.save(euroOrder);
        EuroOrderDTO result = euroOrderMapper.euroOrderToEuroOrderDTO(euroOrder);
        return ResponseEntity.created(new URI("/api/euro-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("euroOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /euro-orders : Updates an existing euroOrder.
     *
     * @param euroOrderDTO the euroOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated euroOrderDTO,
     * or with status 400 (Bad Request) if the euroOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the euroOrderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/euro-orders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderDTO> updateEuroOrder(@Valid @RequestBody EuroOrderDTO euroOrderDTO) throws URISyntaxException {
        log.debug("REST request to update EuroOrder : {}", euroOrderDTO);
        if (euroOrderDTO.getId() == null) {
            return createEuroOrder(euroOrderDTO);
        }
        EuroOrder euroOrder = euroOrderMapper.euroOrderDTOToEuroOrder(euroOrderDTO);
        euroOrder = euroOrderRepository.save(euroOrder);
        EuroOrderDTO result = euroOrderMapper.euroOrderToEuroOrderDTO(euroOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("euroOrder", euroOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /euro-orders : get all the euroOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of euroOrders in body
     */
    @RequestMapping(value = "/euro-orders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<EuroOrderDTO> getAllEuroOrders() {
        log.debug("REST request to get all EuroOrders");
        List<EuroOrder> euroOrders = euroOrderRepository.findAll();
        return euroOrderMapper.euroOrdersToEuroOrderDTOs(euroOrders);
    }

    /**
     * GET  /euro-orders/:id : get the "id" euroOrder.
     *
     * @param id the id of the euroOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the euroOrderDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/euro-orders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderDTO> getEuroOrder(@PathVariable Long id) {
        log.debug("REST request to get EuroOrder : {}", id);
        EuroOrder euroOrder = euroOrderRepository.findOne(id);
        EuroOrderDTO euroOrderDTO = euroOrderMapper.euroOrderToEuroOrderDTO(euroOrder);
        return Optional.ofNullable(euroOrderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /euro-orders/:id : delete the "id" euroOrder.
     *
     * @param id the id of the euroOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/euro-orders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEuroOrder(@PathVariable Long id) {
        log.debug("REST request to delete EuroOrder : {}", id);
        euroOrderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("euroOrder", id.toString())).build();
    }

}
