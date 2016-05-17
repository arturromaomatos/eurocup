package com.eurocup.app.web.rest;

import com.eurocup.app.EurocupApp;
import com.eurocup.app.domain.EuroOrderItem;
import com.eurocup.app.repository.EuroOrderItemRepository;
import com.eurocup.app.web.rest.dto.EuroOrderItemDTO;
import com.eurocup.app.web.rest.mapper.EuroOrderItemMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EuroOrderItemResource REST controller.
 *
 * @see EuroOrderItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EurocupApp.class)
@WebAppConfiguration
@IntegrationTest
public class EuroOrderItemResourceIntTest {


    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    @Inject
    private EuroOrderItemRepository euroOrderItemRepository;

    @Inject
    private EuroOrderItemMapper euroOrderItemMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEuroOrderItemMockMvc;

    private EuroOrderItem euroOrderItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EuroOrderItemResource euroOrderItemResource = new EuroOrderItemResource();
        ReflectionTestUtils.setField(euroOrderItemResource, "euroOrderItemRepository", euroOrderItemRepository);
        ReflectionTestUtils.setField(euroOrderItemResource, "euroOrderItemMapper", euroOrderItemMapper);
        this.restEuroOrderItemMockMvc = MockMvcBuilders.standaloneSetup(euroOrderItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        euroOrderItem = new EuroOrderItem();
        euroOrderItem.setQuantity(DEFAULT_QUANTITY);
        euroOrderItem.setTotalPrice(DEFAULT_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void createEuroOrderItem() throws Exception {
        int databaseSizeBeforeCreate = euroOrderItemRepository.findAll().size();

        // Create the EuroOrderItem
        EuroOrderItemDTO euroOrderItemDTO = euroOrderItemMapper.euroOrderItemToEuroOrderItemDTO(euroOrderItem);

        restEuroOrderItemMockMvc.perform(post("/api/euro-order-items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderItemDTO)))
                .andExpect(status().isCreated());

        // Validate the EuroOrderItem in the database
        List<EuroOrderItem> euroOrderItems = euroOrderItemRepository.findAll();
        assertThat(euroOrderItems).hasSize(databaseSizeBeforeCreate + 1);
        EuroOrderItem testEuroOrderItem = euroOrderItems.get(euroOrderItems.size() - 1);
        assertThat(testEuroOrderItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testEuroOrderItem.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroOrderItemRepository.findAll().size();
        // set the field null
        euroOrderItem.setQuantity(null);

        // Create the EuroOrderItem, which fails.
        EuroOrderItemDTO euroOrderItemDTO = euroOrderItemMapper.euroOrderItemToEuroOrderItemDTO(euroOrderItem);

        restEuroOrderItemMockMvc.perform(post("/api/euro-order-items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderItemDTO)))
                .andExpect(status().isBadRequest());

        List<EuroOrderItem> euroOrderItems = euroOrderItemRepository.findAll();
        assertThat(euroOrderItems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = euroOrderItemRepository.findAll().size();
        // set the field null
        euroOrderItem.setTotalPrice(null);

        // Create the EuroOrderItem, which fails.
        EuroOrderItemDTO euroOrderItemDTO = euroOrderItemMapper.euroOrderItemToEuroOrderItemDTO(euroOrderItem);

        restEuroOrderItemMockMvc.perform(post("/api/euro-order-items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderItemDTO)))
                .andExpect(status().isBadRequest());

        List<EuroOrderItem> euroOrderItems = euroOrderItemRepository.findAll();
        assertThat(euroOrderItems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEuroOrderItems() throws Exception {
        // Initialize the database
        euroOrderItemRepository.saveAndFlush(euroOrderItem);

        // Get all the euroOrderItems
        restEuroOrderItemMockMvc.perform(get("/api/euro-order-items?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(euroOrderItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
                .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getEuroOrderItem() throws Exception {
        // Initialize the database
        euroOrderItemRepository.saveAndFlush(euroOrderItem);

        // Get the euroOrderItem
        restEuroOrderItemMockMvc.perform(get("/api/euro-order-items/{id}", euroOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(euroOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEuroOrderItem() throws Exception {
        // Get the euroOrderItem
        restEuroOrderItemMockMvc.perform(get("/api/euro-order-items/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEuroOrderItem() throws Exception {
        // Initialize the database
        euroOrderItemRepository.saveAndFlush(euroOrderItem);
        int databaseSizeBeforeUpdate = euroOrderItemRepository.findAll().size();

        // Update the euroOrderItem
        EuroOrderItem updatedEuroOrderItem = new EuroOrderItem();
        updatedEuroOrderItem.setId(euroOrderItem.getId());
        updatedEuroOrderItem.setQuantity(UPDATED_QUANTITY);
        updatedEuroOrderItem.setTotalPrice(UPDATED_TOTAL_PRICE);
        EuroOrderItemDTO euroOrderItemDTO = euroOrderItemMapper.euroOrderItemToEuroOrderItemDTO(updatedEuroOrderItem);

        restEuroOrderItemMockMvc.perform(put("/api/euro-order-items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(euroOrderItemDTO)))
                .andExpect(status().isOk());

        // Validate the EuroOrderItem in the database
        List<EuroOrderItem> euroOrderItems = euroOrderItemRepository.findAll();
        assertThat(euroOrderItems).hasSize(databaseSizeBeforeUpdate);
        EuroOrderItem testEuroOrderItem = euroOrderItems.get(euroOrderItems.size() - 1);
        assertThat(testEuroOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testEuroOrderItem.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void deleteEuroOrderItem() throws Exception {
        // Initialize the database
        euroOrderItemRepository.saveAndFlush(euroOrderItem);
        int databaseSizeBeforeDelete = euroOrderItemRepository.findAll().size();

        // Get the euroOrderItem
        restEuroOrderItemMockMvc.perform(delete("/api/euro-order-items/{id}", euroOrderItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EuroOrderItem> euroOrderItems = euroOrderItemRepository.findAll();
        assertThat(euroOrderItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
