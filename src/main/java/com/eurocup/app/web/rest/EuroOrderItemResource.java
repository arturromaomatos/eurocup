package com.eurocup.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eurocup.app.domain.EuroOrderItem;
import com.eurocup.app.repository.EuroOrderItemRepository;
import com.eurocup.app.web.rest.util.HeaderUtil;
import com.eurocup.app.web.rest.dto.EuroOrderItemDTO;
import com.eurocup.app.web.rest.mapper.EuroOrderItemMapper;
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
 * REST controller for managing EuroOrderItem.
 */
@RestController
@RequestMapping("/api")
public class EuroOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(EuroOrderItemResource.class);
        
    @Inject
    private EuroOrderItemRepository euroOrderItemRepository;
    
    @Inject
    private EuroOrderItemMapper euroOrderItemMapper;
    
    /**
     * POST  /euro-order-items : Create a new euroOrderItem.
     *
     * @param euroOrderItemDTO the euroOrderItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new euroOrderItemDTO, or with status 400 (Bad Request) if the euroOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/euro-order-items",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderItemDTO> createEuroOrderItem(@Valid @RequestBody EuroOrderItemDTO euroOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to save EuroOrderItem : {}", euroOrderItemDTO);
        if (euroOrderItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("euroOrderItem", "idexists", "A new euroOrderItem cannot already have an ID")).body(null);
        }
        EuroOrderItem euroOrderItem = euroOrderItemMapper.euroOrderItemDTOToEuroOrderItem(euroOrderItemDTO);
        euroOrderItem = euroOrderItemRepository.save(euroOrderItem);
        EuroOrderItemDTO result = euroOrderItemMapper.euroOrderItemToEuroOrderItemDTO(euroOrderItem);
        return ResponseEntity.created(new URI("/api/euro-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("euroOrderItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /euro-order-items : Updates an existing euroOrderItem.
     *
     * @param euroOrderItemDTO the euroOrderItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated euroOrderItemDTO,
     * or with status 400 (Bad Request) if the euroOrderItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the euroOrderItemDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/euro-order-items",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderItemDTO> updateEuroOrderItem(@Valid @RequestBody EuroOrderItemDTO euroOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to update EuroOrderItem : {}", euroOrderItemDTO);
        if (euroOrderItemDTO.getId() == null) {
            return createEuroOrderItem(euroOrderItemDTO);
        }
        EuroOrderItem euroOrderItem = euroOrderItemMapper.euroOrderItemDTOToEuroOrderItem(euroOrderItemDTO);
        euroOrderItem = euroOrderItemRepository.save(euroOrderItem);
        EuroOrderItemDTO result = euroOrderItemMapper.euroOrderItemToEuroOrderItemDTO(euroOrderItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("euroOrderItem", euroOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /euro-order-items : get all the euroOrderItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of euroOrderItems in body
     */
    @RequestMapping(value = "/euro-order-items",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<EuroOrderItemDTO> getAllEuroOrderItems() {
        log.debug("REST request to get all EuroOrderItems");
        List<EuroOrderItem> euroOrderItems = euroOrderItemRepository.findAll();
        return euroOrderItemMapper.euroOrderItemsToEuroOrderItemDTOs(euroOrderItems);
    }

    /**
     * GET  /euro-order-items/:id : get the "id" euroOrderItem.
     *
     * @param id the id of the euroOrderItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the euroOrderItemDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/euro-order-items/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroOrderItemDTO> getEuroOrderItem(@PathVariable Long id) {
        log.debug("REST request to get EuroOrderItem : {}", id);
        EuroOrderItem euroOrderItem = euroOrderItemRepository.findOne(id);
        EuroOrderItemDTO euroOrderItemDTO = euroOrderItemMapper.euroOrderItemToEuroOrderItemDTO(euroOrderItem);
        return Optional.ofNullable(euroOrderItemDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /euro-order-items/:id : delete the "id" euroOrderItem.
     *
     * @param id the id of the euroOrderItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/euro-order-items/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEuroOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete EuroOrderItem : {}", id);
        euroOrderItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("euroOrderItem", id.toString())).build();
    }

}
