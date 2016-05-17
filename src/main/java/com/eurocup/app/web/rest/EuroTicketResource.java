package com.eurocup.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eurocup.app.domain.EuroTicket;
import com.eurocup.app.repository.EuroTicketRepository;
import com.eurocup.app.web.rest.util.HeaderUtil;
import com.eurocup.app.web.rest.dto.EuroTicketDTO;
import com.eurocup.app.web.rest.mapper.EuroTicketMapper;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing EuroTicket.
 */
@RestController
@RequestMapping("/api")
public class EuroTicketResource {

    private final Logger log = LoggerFactory.getLogger(EuroTicketResource.class);
        
    @Inject
    private EuroTicketRepository euroTicketRepository;
    
    @Inject
    private EuroTicketMapper euroTicketMapper;
    
    /**
     * POST  /euro-tickets : Create a new euroTicket.
     *
     * @param euroTicketDTO the euroTicketDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new euroTicketDTO, or with status 400 (Bad Request) if the euroTicket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/euro-tickets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroTicketDTO> createEuroTicket(@Valid @RequestBody EuroTicketDTO euroTicketDTO) throws URISyntaxException {
        log.debug("REST request to save EuroTicket : {}", euroTicketDTO);
        if (euroTicketDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("euroTicket", "idexists", "A new euroTicket cannot already have an ID")).body(null);
        }
        EuroTicket euroTicket = euroTicketMapper.euroTicketDTOToEuroTicket(euroTicketDTO);
        euroTicket = euroTicketRepository.save(euroTicket);
        EuroTicketDTO result = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);
        return ResponseEntity.created(new URI("/api/euro-tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("euroTicket", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /euro-tickets : Updates an existing euroTicket.
     *
     * @param euroTicketDTO the euroTicketDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated euroTicketDTO,
     * or with status 400 (Bad Request) if the euroTicketDTO is not valid,
     * or with status 500 (Internal Server Error) if the euroTicketDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/euro-tickets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroTicketDTO> updateEuroTicket(@Valid @RequestBody EuroTicketDTO euroTicketDTO) throws URISyntaxException {
        log.debug("REST request to update EuroTicket : {}", euroTicketDTO);
        if (euroTicketDTO.getId() == null) {
            return createEuroTicket(euroTicketDTO);
        }
        EuroTicket euroTicket = euroTicketMapper.euroTicketDTOToEuroTicket(euroTicketDTO);
        euroTicket = euroTicketRepository.save(euroTicket);
        EuroTicketDTO result = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("euroTicket", euroTicketDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /euro-tickets : get all the euroTickets.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of euroTickets in body
     */
    @RequestMapping(value = "/euro-tickets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<EuroTicketDTO> getAllEuroTickets(@RequestParam(required = false) String filter) {
        if ("name-is-null".equals(filter)) {
            log.debug("REST request to get all EuroTickets where name is null");
            return StreamSupport
                .stream(euroTicketRepository.findAll().spliterator(), false)
                .filter(euroTicket -> euroTicket.getName() == null)
                .map(euroTicketMapper::euroTicketToEuroTicketDTO)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all EuroTickets");
        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        return euroTicketMapper.euroTicketsToEuroTicketDTOs(euroTickets);
    }

    /**
     * GET  /euro-tickets/:id : get the "id" euroTicket.
     *
     * @param id the id of the euroTicketDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the euroTicketDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/euro-tickets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EuroTicketDTO> getEuroTicket(@PathVariable Long id) {
        log.debug("REST request to get EuroTicket : {}", id);
        EuroTicket euroTicket = euroTicketRepository.findOne(id);
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);
        return Optional.ofNullable(euroTicketDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /euro-tickets/:id : delete the "id" euroTicket.
     *
     * @param id the id of the euroTicketDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/euro-tickets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEuroTicket(@PathVariable Long id) {
        log.debug("REST request to delete EuroTicket : {}", id);
        euroTicketRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("euroTicket", id.toString())).build();
    }

}
