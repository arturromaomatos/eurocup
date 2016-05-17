package com.eurocup.app.web.rest;

import com.eurocup.app.EurocupApp;
import com.eurocup.app.domain.EuroTicket;
import com.eurocup.app.repository.EuroTicketRepository;
import com.eurocup.app.web.rest.dto.EuroTicketDTO;
import com.eurocup.app.web.rest.mapper.EuroTicketMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EuroTicketResource REST controller.
 *
 * @see EuroTicketResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EurocupApp.class)
@WebAppConfiguration
@IntegrationTest
public class EuroTicketResourceIntTest {

    private static final String DEFAULT_MATCH = "AAAAA";
    private static final String UPDATED_MATCH = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_PHASE = "AAAAA";
    private static final String UPDATED_PHASE = "BBBBB";
    private static final String DEFAULT_MATCHGROUP = "AAAAA";
    private static final String UPDATED_MATCHGROUP = "BBBBB";

    private static final LocalDate DEFAULT_MATCH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MATCH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_MATCH_HOUR = "AAAAA";
    private static final String UPDATED_MATCH_HOUR = "BBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final String DEFAULT_IMAGE = "AAAAA";
    private static final String UPDATED_IMAGE = "BBBBB";

    private static final Long DEFAULT_TOTAL_TICKETS = 1L;
    private static final Long UPDATED_TOTAL_TICKETS = 2L;

    private static final Long DEFAULT_NR_OF_TICKETS = 1L;
    private static final Long UPDATED_NR_OF_TICKETS = 2L;

    @Inject
    private EuroTicketRepository euroTicketRepository;

    @Inject
    private EuroTicketMapper euroTicketMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEuroTicketMockMvc;

    private EuroTicket euroTicket;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EuroTicketResource euroTicketResource = new EuroTicketResource();
        ReflectionTestUtils.setField(euroTicketResource, "euroTicketRepository", euroTicketRepository);
        ReflectionTestUtils.setField(euroTicketResource, "euroTicketMapper", euroTicketMapper);
        this.restEuroTicketMockMvc = MockMvcBuilders.standaloneSetup(euroTicketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        euroTicket = new EuroTicket();
        euroTicket.setMatch(DEFAULT_MATCH);
        euroTicket.setLocation(DEFAULT_LOCATION);
        euroTicket.setPhase(DEFAULT_PHASE);
        euroTicket.setMatchgroup(DEFAULT_MATCHGROUP);
        euroTicket.setMatchDate(DEFAULT_MATCH_DATE);
        euroTicket.setMatchHour(DEFAULT_MATCH_HOUR);
        euroTicket.setPrice(DEFAULT_PRICE);
        euroTicket.setImage(DEFAULT_IMAGE);
        euroTicket.setTotalTickets(DEFAULT_TOTAL_TICKETS);
        euroTicket.setNrOfTickets(DEFAULT_NR_OF_TICKETS);
    }

    @Test
    @Transactional
    public void createEuroTicket() throws Exception {
        int databaseSizeBeforeCreate = euroTicketRepository.findAll().size();

        // Create the EuroTicket
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isCreated());

        // Validate the EuroTicket in the database
        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeCreate + 1);
        EuroTicket testEuroTicket = euroTickets.get(euroTickets.size() - 1);
        assertThat(testEuroTicket.getMatch()).isEqualTo(DEFAULT_MATCH);
        assertThat(testEuroTicket.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testEuroTicket.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testEuroTicket.getMatchgroup()).isEqualTo(DEFAULT_MATCHGROUP);
        assertThat(testEuroTicket.getMatchDate()).isEqualTo(DEFAULT_MATCH_DATE);
        assertThat(testEuroTicket.getMatchHour()).isEqualTo(DEFAULT_MATCH_HOUR);
        assertThat(testEuroTicket.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testEuroTicket.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testEuroTicket.getTotalTickets()).isEqualTo(DEFAULT_TOTAL_TICKETS);
        assertThat(testEuroTicket.getNrOfTickets()).isEqualTo(DEFAULT_NR_OF_TICKETS);
    }

    @Test
    @Transactional
    public void checkMatchIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroTicketRepository.findAll().size();
        // set the field null
        euroTicket.setMatch(null);

        // Create the EuroTicket, which fails.
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isBadRequest());

        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroTicketRepository.findAll().size();
        // set the field null
        euroTicket.setLocation(null);

        // Create the EuroTicket, which fails.
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isBadRequest());

        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroTicketRepository.findAll().size();
        // set the field null
        euroTicket.setPhase(null);

        // Create the EuroTicket, which fails.
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isBadRequest());

        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatchgroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroTicketRepository.findAll().size();
        // set the field null
        euroTicket.setMatchgroup(null);

        // Create the EuroTicket, which fails.
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isBadRequest());

        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatchDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroTicketRepository.findAll().size();
        // set the field null
        euroTicket.setMatchDate(null);

        // Create the EuroTicket, which fails.
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isBadRequest());

        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroTicketRepository.findAll().size();
        // set the field null
        euroTicket.setPrice(null);

        // Create the EuroTicket, which fails.
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isBadRequest());

        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalTicketsIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroTicketRepository.findAll().size();
        // set the field null
        euroTicket.setTotalTickets(null);

        // Create the EuroTicket, which fails.
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isBadRequest());

        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNrOfTicketsIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroTicketRepository.findAll().size();
        // set the field null
        euroTicket.setNrOfTickets(null);

        // Create the EuroTicket, which fails.
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(euroTicket);

        restEuroTicketMockMvc.perform(post("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isBadRequest());

        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEuroTickets() throws Exception {
        // Initialize the database
        euroTicketRepository.saveAndFlush(euroTicket);

        // Get all the euroTickets
        restEuroTicketMockMvc.perform(get("/api/euro-tickets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(euroTicket.getId().intValue())))
                .andExpect(jsonPath("$.[*].match").value(hasItem(DEFAULT_MATCH.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
                .andExpect(jsonPath("$.[*].matchgroup").value(hasItem(DEFAULT_MATCHGROUP.toString())))
                .andExpect(jsonPath("$.[*].matchDate").value(hasItem(DEFAULT_MATCH_DATE.toString())))
                .andExpect(jsonPath("$.[*].matchHour").value(hasItem(DEFAULT_MATCH_HOUR.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
                .andExpect(jsonPath("$.[*].totalTickets").value(hasItem(DEFAULT_TOTAL_TICKETS.intValue())))
                .andExpect(jsonPath("$.[*].nrOfTickets").value(hasItem(DEFAULT_NR_OF_TICKETS.intValue())));
    }

    @Test
    @Transactional
    public void getEuroTicket() throws Exception {
        // Initialize the database
        euroTicketRepository.saveAndFlush(euroTicket);

        // Get the euroTicket
        restEuroTicketMockMvc.perform(get("/api/euro-tickets/{id}", euroTicket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(euroTicket.getId().intValue()))
            .andExpect(jsonPath("$.match").value(DEFAULT_MATCH.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.matchgroup").value(DEFAULT_MATCHGROUP.toString()))
            .andExpect(jsonPath("$.matchDate").value(DEFAULT_MATCH_DATE.toString()))
            .andExpect(jsonPath("$.matchHour").value(DEFAULT_MATCH_HOUR.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.totalTickets").value(DEFAULT_TOTAL_TICKETS.intValue()))
            .andExpect(jsonPath("$.nrOfTickets").value(DEFAULT_NR_OF_TICKETS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEuroTicket() throws Exception {
        // Get the euroTicket
        restEuroTicketMockMvc.perform(get("/api/euro-tickets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEuroTicket() throws Exception {
        // Initialize the database
        euroTicketRepository.saveAndFlush(euroTicket);
        int databaseSizeBeforeUpdate = euroTicketRepository.findAll().size();

        // Update the euroTicket
        EuroTicket updatedEuroTicket = new EuroTicket();
        updatedEuroTicket.setId(euroTicket.getId());
        updatedEuroTicket.setMatch(UPDATED_MATCH);
        updatedEuroTicket.setLocation(UPDATED_LOCATION);
        updatedEuroTicket.setPhase(UPDATED_PHASE);
        updatedEuroTicket.setMatchgroup(UPDATED_MATCHGROUP);
        updatedEuroTicket.setMatchDate(UPDATED_MATCH_DATE);
        updatedEuroTicket.setMatchHour(UPDATED_MATCH_HOUR);
        updatedEuroTicket.setPrice(UPDATED_PRICE);
        updatedEuroTicket.setImage(UPDATED_IMAGE);
        updatedEuroTicket.setTotalTickets(UPDATED_TOTAL_TICKETS);
        updatedEuroTicket.setNrOfTickets(UPDATED_NR_OF_TICKETS);
        EuroTicketDTO euroTicketDTO = euroTicketMapper.euroTicketToEuroTicketDTO(updatedEuroTicket);

        restEuroTicketMockMvc.perform(put("/api/euro-tickets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroTicketDTO)))
                .andExpect(status().isOk());

        // Validate the EuroTicket in the database
        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeUpdate);
        EuroTicket testEuroTicket = euroTickets.get(euroTickets.size() - 1);
        assertThat(testEuroTicket.getMatch()).isEqualTo(UPDATED_MATCH);
        assertThat(testEuroTicket.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEuroTicket.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testEuroTicket.getMatchgroup()).isEqualTo(UPDATED_MATCHGROUP);
        assertThat(testEuroTicket.getMatchDate()).isEqualTo(UPDATED_MATCH_DATE);
        assertThat(testEuroTicket.getMatchHour()).isEqualTo(UPDATED_MATCH_HOUR);
        assertThat(testEuroTicket.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testEuroTicket.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testEuroTicket.getTotalTickets()).isEqualTo(UPDATED_TOTAL_TICKETS);
        assertThat(testEuroTicket.getNrOfTickets()).isEqualTo(UPDATED_NR_OF_TICKETS);
    }

    @Test
    @Transactional
    public void deleteEuroTicket() throws Exception {
        // Initialize the database
        euroTicketRepository.saveAndFlush(euroTicket);
        int databaseSizeBeforeDelete = euroTicketRepository.findAll().size();

        // Get the euroTicket
        restEuroTicketMockMvc.perform(delete("/api/euro-tickets/{id}", euroTicket.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EuroTicket> euroTickets = euroTicketRepository.findAll();
        assertThat(euroTickets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
