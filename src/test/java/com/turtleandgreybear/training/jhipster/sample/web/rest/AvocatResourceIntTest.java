package com.turtleandgreybear.training.jhipster.sample.web.rest;

import com.turtleandgreybear.training.jhipster.sample.JhipsterSampleApplicationApp;

import com.turtleandgreybear.training.jhipster.sample.domain.Avocat;
import com.turtleandgreybear.training.jhipster.sample.repository.AvocatRepository;
import com.turtleandgreybear.training.jhipster.sample.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.turtleandgreybear.training.jhipster.sample.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AvocatResource REST controller.
 *
 * @see AvocatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class AvocatResourceIntTest {

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private AvocatRepository avocatRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAvocatMockMvc;

    private Avocat avocat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvocatResource avocatResource = new AvocatResource(avocatRepository);
        this.restAvocatMockMvc = MockMvcBuilders.standaloneSetup(avocatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avocat createEntity(EntityManager em) {
        Avocat avocat = new Avocat()
            .adresse(DEFAULT_ADRESSE)
            .nom(DEFAULT_NOM);
        return avocat;
    }

    @Before
    public void initTest() {
        avocat = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvocat() throws Exception {
        int databaseSizeBeforeCreate = avocatRepository.findAll().size();

        // Create the Avocat
        restAvocatMockMvc.perform(post("/api/avocats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avocat)))
            .andExpect(status().isCreated());

        // Validate the Avocat in the database
        List<Avocat> avocatList = avocatRepository.findAll();
        assertThat(avocatList).hasSize(databaseSizeBeforeCreate + 1);
        Avocat testAvocat = avocatList.get(avocatList.size() - 1);
        assertThat(testAvocat.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testAvocat.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createAvocatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avocatRepository.findAll().size();

        // Create the Avocat with an existing ID
        avocat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvocatMockMvc.perform(post("/api/avocats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avocat)))
            .andExpect(status().isBadRequest());

        // Validate the Avocat in the database
        List<Avocat> avocatList = avocatRepository.findAll();
        assertThat(avocatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAvocats() throws Exception {
        // Initialize the database
        avocatRepository.saveAndFlush(avocat);

        // Get all the avocatList
        restAvocatMockMvc.perform(get("/api/avocats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avocat.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void getAvocat() throws Exception {
        // Initialize the database
        avocatRepository.saveAndFlush(avocat);

        // Get the avocat
        restAvocatMockMvc.perform(get("/api/avocats/{id}", avocat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avocat.getId().intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAvocat() throws Exception {
        // Get the avocat
        restAvocatMockMvc.perform(get("/api/avocats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvocat() throws Exception {
        // Initialize the database
        avocatRepository.saveAndFlush(avocat);
        int databaseSizeBeforeUpdate = avocatRepository.findAll().size();

        // Update the avocat
        Avocat updatedAvocat = avocatRepository.findOne(avocat.getId());
        // Disconnect from session so that the updates on updatedAvocat are not directly saved in db
        em.detach(updatedAvocat);
        updatedAvocat
            .adresse(UPDATED_ADRESSE)
            .nom(UPDATED_NOM);

        restAvocatMockMvc.perform(put("/api/avocats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvocat)))
            .andExpect(status().isOk());

        // Validate the Avocat in the database
        List<Avocat> avocatList = avocatRepository.findAll();
        assertThat(avocatList).hasSize(databaseSizeBeforeUpdate);
        Avocat testAvocat = avocatList.get(avocatList.size() - 1);
        assertThat(testAvocat.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAvocat.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingAvocat() throws Exception {
        int databaseSizeBeforeUpdate = avocatRepository.findAll().size();

        // Create the Avocat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvocatMockMvc.perform(put("/api/avocats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avocat)))
            .andExpect(status().isCreated());

        // Validate the Avocat in the database
        List<Avocat> avocatList = avocatRepository.findAll();
        assertThat(avocatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvocat() throws Exception {
        // Initialize the database
        avocatRepository.saveAndFlush(avocat);
        int databaseSizeBeforeDelete = avocatRepository.findAll().size();

        // Get the avocat
        restAvocatMockMvc.perform(delete("/api/avocats/{id}", avocat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Avocat> avocatList = avocatRepository.findAll();
        assertThat(avocatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avocat.class);
        Avocat avocat1 = new Avocat();
        avocat1.setId(1L);
        Avocat avocat2 = new Avocat();
        avocat2.setId(avocat1.getId());
        assertThat(avocat1).isEqualTo(avocat2);
        avocat2.setId(2L);
        assertThat(avocat1).isNotEqualTo(avocat2);
        avocat1.setId(null);
        assertThat(avocat1).isNotEqualTo(avocat2);
    }
}
