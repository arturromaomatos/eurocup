package com.eurocup.app.web.rest;

import com.eurocup.app.EurocupApp;
import com.eurocup.app.domain.EuroOrderPayment;
import com.eurocup.app.repository.EuroOrderPaymentRepository;
import com.eurocup.app.web.rest.dto.EuroOrderPaymentDTO;
import com.eurocup.app.web.rest.mapper.EuroOrderPaymentMapper;

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
 * Test class for the EuroOrderPaymentResource REST controller.
 *
 * @see EuroOrderPaymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EurocupApp.class)
@WebAppConfiguration
@IntegrationTest
public class EuroOrderPaymentResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_METHOD = "AAAAA";
    private static final String UPDATED_METHOD = "BBBBB";
    private static final String DEFAULT_CARD_NUMBER = "AAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBB";
    private static final String DEFAULT_MONTH = "AAAAA";
    private static final String UPDATED_MONTH = "BBBBB";
    private static final String DEFAULT_YEAR = "AAAAA";
    private static final String UPDATED_YEAR = "BBBBB";
    private static final String DEFAULT_CVC = "AAAAA";
    private static final String UPDATED_CVC = "BBBBB";
    private static final String DEFAULT_PAYMENT_ENTITY = "AAAAA";
    private static final String UPDATED_PAYMENT_ENTITY = "BBBBB";
    private static final String DEFAULT_PAYMENT_REFERENCE = "AAAAA";
    private static final String UPDATED_PAYMENT_REFERENCE = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);
    private static final String DEFAULT_CARD_CUST_NAME = "AAAAA";
    private static final String UPDATED_CARD_CUST_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_PAYMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PAYMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PAYMENT_DATE_STR = dateTimeFormatter.format(DEFAULT_PAYMENT_DATE);
    private static final String DEFAULT_IBAN = "AAAAA";
    private static final String UPDATED_IBAN = "BBBBB";

    @Inject
    private EuroOrderPaymentRepository euroOrderPaymentRepository;

    @Inject
    private EuroOrderPaymentMapper euroOrderPaymentMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEuroOrderPaymentMockMvc;

    private EuroOrderPayment euroOrderPayment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EuroOrderPaymentResource euroOrderPaymentResource = new EuroOrderPaymentResource();
        ReflectionTestUtils.setField(euroOrderPaymentResource, "euroOrderPaymentRepository", euroOrderPaymentRepository);
        ReflectionTestUtils.setField(euroOrderPaymentResource, "euroOrderPaymentMapper", euroOrderPaymentMapper);
        this.restEuroOrderPaymentMockMvc = MockMvcBuilders.standaloneSetup(euroOrderPaymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        euroOrderPayment = new EuroOrderPayment();
        euroOrderPayment.setMethod(DEFAULT_METHOD);
        euroOrderPayment.setCardNumber(DEFAULT_CARD_NUMBER);
        euroOrderPayment.setMonth(DEFAULT_MONTH);
        euroOrderPayment.setYear(DEFAULT_YEAR);
        euroOrderPayment.setCvc(DEFAULT_CVC);
        euroOrderPayment.setPaymentEntity(DEFAULT_PAYMENT_ENTITY);
        euroOrderPayment.setPaymentReference(DEFAULT_PAYMENT_REFERENCE);
        euroOrderPayment.setTotalPrice(DEFAULT_TOTAL_PRICE);
        euroOrderPayment.setCardCustName(DEFAULT_CARD_CUST_NAME);
        euroOrderPayment.setPaymentDate(DEFAULT_PAYMENT_DATE);
        euroOrderPayment.setIban(DEFAULT_IBAN);
    }

    @Test
    @Transactional
    public void createEuroOrderPayment() throws Exception {
        int databaseSizeBeforeCreate = euroOrderPaymentRepository.findAll().size();

        // Create the EuroOrderPayment
        EuroOrderPaymentDTO euroOrderPaymentDTO = euroOrderPaymentMapper.euroOrderPaymentToEuroOrderPaymentDTO(euroOrderPayment);

        restEuroOrderPaymentMockMvc.perform(post("/api/euro-order-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderPaymentDTO)))
                .andExpect(status().isCreated());

        // Validate the EuroOrderPayment in the database
        List<EuroOrderPayment> euroOrderPayments = euroOrderPaymentRepository.findAll();
        assertThat(euroOrderPayments).hasSize(databaseSizeBeforeCreate + 1);
        EuroOrderPayment testEuroOrderPayment = euroOrderPayments.get(euroOrderPayments.size() - 1);
        assertThat(testEuroOrderPayment.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testEuroOrderPayment.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testEuroOrderPayment.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testEuroOrderPayment.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testEuroOrderPayment.getCvc()).isEqualTo(DEFAULT_CVC);
        assertThat(testEuroOrderPayment.getPaymentEntity()).isEqualTo(DEFAULT_PAYMENT_ENTITY);
        assertThat(testEuroOrderPayment.getPaymentReference()).isEqualTo(DEFAULT_PAYMENT_REFERENCE);
        assertThat(testEuroOrderPayment.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testEuroOrderPayment.getCardCustName()).isEqualTo(DEFAULT_CARD_CUST_NAME);
        assertThat(testEuroOrderPayment.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testEuroOrderPayment.getIban()).isEqualTo(DEFAULT_IBAN);
    }

    @Test
    @Transactional
    public void checkMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroOrderPaymentRepository.findAll().size();
        // set the field null
        euroOrderPayment.setMethod(null);

        // Create the EuroOrderPayment, which fails.
        EuroOrderPaymentDTO euroOrderPaymentDTO = euroOrderPaymentMapper.euroOrderPaymentToEuroOrderPaymentDTO(euroOrderPayment);

        restEuroOrderPaymentMockMvc.perform(post("/api/euro-order-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderPaymentDTO)))
                .andExpect(status().isBadRequest());

        List<EuroOrderPayment> euroOrderPayments = euroOrderPaymentRepository.findAll();
        assertThat(euroOrderPayments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEuroOrderPayments() throws Exception {
        // Initialize the database
        euroOrderPaymentRepository.saveAndFlush(euroOrderPayment);

        // Get all the euroOrderPayments
        restEuroOrderPaymentMockMvc.perform(get("/api/euro-order-payments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(euroOrderPayment.getId().intValue())))
                .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
                .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
                .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC.toString())))
                .andExpect(jsonPath("$.[*].paymentEntity").value(hasItem(DEFAULT_PAYMENT_ENTITY.toString())))
                .andExpect(jsonPath("$.[*].paymentReference").value(hasItem(DEFAULT_PAYMENT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].cardCustName").value(hasItem(DEFAULT_CARD_CUST_NAME.toString())))
                .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE_STR)))
                .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN.toString())));
    }

    @Test
    @Transactional
    public void getEuroOrderPayment() throws Exception {
        // Initialize the database
        euroOrderPaymentRepository.saveAndFlush(euroOrderPayment);

        // Get the euroOrderPayment
        restEuroOrderPaymentMockMvc.perform(get("/api/euro-order-payments/{id}", euroOrderPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(euroOrderPayment.getId().intValue()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.cvc").value(DEFAULT_CVC.toString()))
            .andExpect(jsonPath("$.paymentEntity").value(DEFAULT_PAYMENT_ENTITY.toString()))
            .andExpect(jsonPath("$.paymentReference").value(DEFAULT_PAYMENT_REFERENCE.toString()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.cardCustName").value(DEFAULT_CARD_CUST_NAME.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE_STR))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEuroOrderPayment() throws Exception {
        // Get the euroOrderPayment
        restEuroOrderPaymentMockMvc.perform(get("/api/euro-order-payments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEuroOrderPayment() throws Exception {
        // Initialize the database
        euroOrderPaymentRepository.saveAndFlush(euroOrderPayment);
        int databaseSizeBeforeUpdate = euroOrderPaymentRepository.findAll().size();

        // Update the euroOrderPayment
        EuroOrderPayment updatedEuroOrderPayment = new EuroOrderPayment();
        updatedEuroOrderPayment.setId(euroOrderPayment.getId());
        updatedEuroOrderPayment.setMethod(UPDATED_METHOD);
        updatedEuroOrderPayment.setCardNumber(UPDATED_CARD_NUMBER);
        updatedEuroOrderPayment.setMonth(UPDATED_MONTH);
        updatedEuroOrderPayment.setYear(UPDATED_YEAR);
        updatedEuroOrderPayment.setCvc(UPDATED_CVC);
        updatedEuroOrderPayment.setPaymentEntity(UPDATED_PAYMENT_ENTITY);
        updatedEuroOrderPayment.setPaymentReference(UPDATED_PAYMENT_REFERENCE);
        updatedEuroOrderPayment.setTotalPrice(UPDATED_TOTAL_PRICE);
        updatedEuroOrderPayment.setCardCustName(UPDATED_CARD_CUST_NAME);
        updatedEuroOrderPayment.setPaymentDate(UPDATED_PAYMENT_DATE);
        updatedEuroOrderPayment.setIban(UPDATED_IBAN);
        EuroOrderPaymentDTO euroOrderPaymentDTO = euroOrderPaymentMapper.euroOrderPaymentToEuroOrderPaymentDTO(updatedEuroOrderPayment);

        restEuroOrderPaymentMockMvc.perform(put("/api/euro-order-payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderPaymentDTO)))
                .andExpect(status().isOk());

        // Validate the EuroOrderPayment in the database
        List<EuroOrderPayment> euroOrderPayments = euroOrderPaymentRepository.findAll();
        assertThat(euroOrderPayments).hasSize(databaseSizeBeforeUpdate);
        EuroOrderPayment testEuroOrderPayment = euroOrderPayments.get(euroOrderPayments.size() - 1);
        assertThat(testEuroOrderPayment.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testEuroOrderPayment.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testEuroOrderPayment.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testEuroOrderPayment.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testEuroOrderPayment.getCvc()).isEqualTo(UPDATED_CVC);
        assertThat(testEuroOrderPayment.getPaymentEntity()).isEqualTo(UPDATED_PAYMENT_ENTITY);
        assertThat(testEuroOrderPayment.getPaymentReference()).isEqualTo(UPDATED_PAYMENT_REFERENCE);
        assertThat(testEuroOrderPayment.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testEuroOrderPayment.getCardCustName()).isEqualTo(UPDATED_CARD_CUST_NAME);
        assertThat(testEuroOrderPayment.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testEuroOrderPayment.getIban()).isEqualTo(UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void deleteEuroOrderPayment() throws Exception {
        // Initialize the database
        euroOrderPaymentRepository.saveAndFlush(euroOrderPayment);
        int databaseSizeBeforeDelete = euroOrderPaymentRepository.findAll().size();

        // Get the euroOrderPayment
        restEuroOrderPaymentMockMvc.perform(delete("/api/euro-order-payments/{id}", euroOrderPayment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EuroOrderPayment> euroOrderPayments = euroOrderPaymentRepository.findAll();
        assertThat(euroOrderPayments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
