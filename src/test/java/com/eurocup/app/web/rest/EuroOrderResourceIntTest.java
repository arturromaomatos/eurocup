package com.eurocup.app.web.rest;

import com.eurocup.app.EurocupApp;
import com.eurocup.app.domain.EuroOrder;
import com.eurocup.app.repository.EuroOrderRepository;
import com.eurocup.app.web.rest.dto.EuroOrderDTO;
import com.eurocup.app.web.rest.mapper.EuroOrderMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EuroOrderResource REST controller.
 *
 * @see EuroOrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EurocupApp.class)
@WebAppConfiguration
@IntegrationTest
public class EuroOrderResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ORDER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ORDER_DATE_STR = dateTimeFormatter.format(DEFAULT_ORDER_DATE);

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);
    private static final String DEFAULT_PAYMENT_STATUS = "AAAAA";
    private static final String UPDATED_PAYMENT_STATUS = "BBBBB";

    @Inject
    private EuroOrderRepository euroOrderRepository;

    @Inject
    private EuroOrderMapper euroOrderMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEuroOrderMockMvc;

    private EuroOrder euroOrder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EuroOrderResource euroOrderResource = new EuroOrderResource();
        ReflectionTestUtils.setField(euroOrderResource, "euroOrderRepository", euroOrderRepository);
        ReflectionTestUtils.setField(euroOrderResource, "euroOrderMapper", euroOrderMapper);
        this.restEuroOrderMockMvc = MockMvcBuilders.standaloneSetup(euroOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        euroOrder = new EuroOrder();
        euroOrder.setOrderDate(DEFAULT_ORDER_DATE);
        euroOrder.setTotalPrice(DEFAULT_TOTAL_PRICE);
        euroOrder.setPaymentStatus(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void createEuroOrder() throws Exception {
        int databaseSizeBeforeCreate = euroOrderRepository.findAll().size();

        // Create the EuroOrder
        EuroOrderDTO euroOrderDTO = euroOrderMapper.euroOrderToEuroOrderDTO(euroOrder);

        restEuroOrderMockMvc.perform(post("/api/euro-orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderDTO)))
                .andExpect(status().isCreated());

        // Validate the EuroOrder in the database
        List<EuroOrder> euroOrders = euroOrderRepository.findAll();
        assertThat(euroOrders).hasSize(databaseSizeBeforeCreate + 1);
        EuroOrder testEuroOrder = euroOrders.get(euroOrders.size() - 1);
        assertThat(testEuroOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testEuroOrder.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testEuroOrder.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroOrderRepository.findAll().size();
        // set the field null
        euroOrder.setOrderDate(null);

        // Create the EuroOrder, which fails.
        EuroOrderDTO euroOrderDTO = euroOrderMapper.euroOrderToEuroOrderDTO(euroOrder);

        restEuroOrderMockMvc.perform(post("/api/euro-orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderDTO)))
                .andExpect(status().isBadRequest());

        List<EuroOrder> euroOrders = euroOrderRepository.findAll();
        assertThat(euroOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroOrderRepository.findAll().size();
        // set the field null
        euroOrder.setPaymentStatus(null);

        // Create the EuroOrder, which fails.
        EuroOrderDTO euroOrderDTO = euroOrderMapper.euroOrderToEuroOrderDTO(euroOrder);

        restEuroOrderMockMvc.perform(post("/api/euro-orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderDTO)))
                .andExpect(status().isBadRequest());

        List<EuroOrder> euroOrders = euroOrderRepository.findAll();
        assertThat(euroOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEuroOrders() throws Exception {
        // Initialize the database
        euroOrderRepository.saveAndFlush(euroOrder);

        // Get all the euroOrders
        restEuroOrderMockMvc.perform(get("/api/euro-orders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(euroOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE_STR)))
                .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getEuroOrder() throws Exception {
        // Initialize the database
        euroOrderRepository.saveAndFlush(euroOrder);

        // Get the euroOrder
        restEuroOrderMockMvc.perform(get("/api/euro-orders/{id}", euroOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(euroOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE_STR))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEuroOrder() throws Exception {
        // Get the euroOrder
        restEuroOrderMockMvc.perform(get("/api/euro-orders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEuroOrder() throws Exception {
        // Initialize the database
        euroOrderRepository.saveAndFlush(euroOrder);
        int databaseSizeBeforeUpdate = euroOrderRepository.findAll().size();

        // Update the euroOrder
        EuroOrder updatedEuroOrder = new EuroOrder();
        updatedEuroOrder.setId(euroOrder.getId());
        updatedEuroOrder.setOrderDate(UPDATED_ORDER_DATE);
        updatedEuroOrder.setTotalPrice(UPDATED_TOTAL_PRICE);
        updatedEuroOrder.setPaymentStatus(UPDATED_PAYMENT_STATUS);
        EuroOrderDTO euroOrderDTO = euroOrderMapper.euroOrderToEuroOrderDTO(updatedEuroOrder);

        restEuroOrderMockMvc.perform(put("/api/euro-orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderDTO)))
                .andExpect(status().isOk());

        // Validate the EuroOrder in the database
        List<EuroOrder> euroOrders = euroOrderRepository.findAll();
        assertThat(euroOrders).hasSize(databaseSizeBeforeUpdate);
        EuroOrder testEuroOrder = euroOrders.get(euroOrders.size() - 1);
        assertThat(testEuroOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testEuroOrder.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testEuroOrder.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void deleteEuroOrder() throws Exception {
        // Initialize the database
        euroOrderRepository.saveAndFlush(euroOrder);
        int databaseSizeBeforeDelete = euroOrderRepository.findAll().size();

        // Get the euroOrder
        restEuroOrderMockMvc.perform(delete("/api/euro-orders/{id}", euroOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EuroOrder> euroOrders = euroOrderRepository.findAll();
        assertThat(euroOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
