package com.turtleandgreybear.training.jhipster.sample.web.rest;

import com.turtleandgreybear.training.jhipster.sample.JhipsterSampleApplicationApp;

import com.turtleandgreybear.training.jhipster.sample.domain.Dossier;
import com.turtleandgreybear.training.jhipster.sample.repository.DossierRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.turtleandgreybear.training.jhipster.sample.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.turtleandgreybear.training.jhipster.sample.domain.enumeration.DossierStatut;
/**
 * Test class for the DossierResource REST controller.
 *
 * @see DossierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DossierResourceIntTest {

    private static final LocalDate DEFAULT_RECOURS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECOURS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final DossierStatut DEFAULT_STATUT = DossierStatut.RECU;
    private static final DossierStatut UPDATED_STATUT = DossierStatut.COMPLET;

    private static final Boolean DEFAULT_NUMERISE = false;
    private static final Boolean UPDATED_NUMERISE = true;

    private static final String DEFAULT_AVOCAT = "AAAAAAAAAA";
    private static final String UPDATED_AVOCAT = "BBBBBBBBBB";

    private static final String DEFAULT_SECRETAIRE = "AAAAAAAAAA";
    private static final String UPDATED_SECRETAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSESSEUR = "AAAAAAAAAA";
    private static final String UPDATED_ASSESSEUR = "BBBBBBBBBB";

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDossierMockMvc;

    private Dossier dossier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DossierResource dossierResource = new DossierResource(dossierRepository);
        this.restDossierMockMvc = MockMvcBuilders.standaloneSetup(dossierResource)
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
    public static Dossier createEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .recoursDate(DEFAULT_RECOURS_DATE)
            .numero(DEFAULT_NUMERO)
            .statut(DEFAULT_STATUT)
            .numerise(DEFAULT_NUMERISE)
            .avocat(DEFAULT_AVOCAT)
            .secretaire(DEFAULT_SECRETAIRE)
            .assesseur(DEFAULT_ASSESSEUR);
        return dossier;
    }

    @Before
    public void initTest() {
        dossier = createEntity(em);
    }

    @Test
    @Transactional
    public void createDossier() throws Exception {
        int databaseSizeBeforeCreate = dossierRepository.findAll().size();

        // Create the Dossier
        restDossierMockMvc.perform(post("/api/dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isCreated());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeCreate + 1);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getRecoursDate()).isEqualTo(DEFAULT_RECOURS_DATE);
        assertThat(testDossier.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDossier.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDossier.isNumerise()).isEqualTo(DEFAULT_NUMERISE);
        assertThat(testDossier.getAvocat()).isEqualTo(DEFAULT_AVOCAT);
        assertThat(testDossier.getSecretaire()).isEqualTo(DEFAULT_SECRETAIRE);
        assertThat(testDossier.getAssesseur()).isEqualTo(DEFAULT_ASSESSEUR);
    }

    @Test
    @Transactional
    public void createDossierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dossierRepository.findAll().size();

        // Create the Dossier with an existing ID
        dossier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierMockMvc.perform(post("/api/dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDossiers() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList
        restDossierMockMvc.perform(get("/api/dossiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].recoursDate").value(hasItem(DEFAULT_RECOURS_DATE.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].numerise").value(hasItem(DEFAULT_NUMERISE.booleanValue())))
            .andExpect(jsonPath("$.[*].avocat").value(hasItem(DEFAULT_AVOCAT.toString())))
            .andExpect(jsonPath("$.[*].secretaire").value(hasItem(DEFAULT_SECRETAIRE.toString())))
            .andExpect(jsonPath("$.[*].assesseur").value(hasItem(DEFAULT_ASSESSEUR.toString())));
    }

    @Test
    @Transactional
    public void getDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get the dossier
        restDossierMockMvc.perform(get("/api/dossiers/{id}", dossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dossier.getId().intValue()))
            .andExpect(jsonPath("$.recoursDate").value(DEFAULT_RECOURS_DATE.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.numerise").value(DEFAULT_NUMERISE.booleanValue()))
            .andExpect(jsonPath("$.avocat").value(DEFAULT_AVOCAT.toString()))
            .andExpect(jsonPath("$.secretaire").value(DEFAULT_SECRETAIRE.toString()))
            .andExpect(jsonPath("$.assesseur").value(DEFAULT_ASSESSEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDossier() throws Exception {
        // Get the dossier
        restDossierMockMvc.perform(get("/api/dossiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier
        Dossier updatedDossier = dossierRepository.findOne(dossier.getId());
        // Disconnect from session so that the updates on updatedDossier are not directly saved in db
        em.detach(updatedDossier);
        updatedDossier
            .recoursDate(UPDATED_RECOURS_DATE)
            .numero(UPDATED_NUMERO)
            .statut(UPDATED_STATUT)
            .numerise(UPDATED_NUMERISE)
            .avocat(UPDATED_AVOCAT)
            .secretaire(UPDATED_SECRETAIRE)
            .assesseur(UPDATED_ASSESSEUR);

        restDossierMockMvc.perform(put("/api/dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDossier)))
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getRecoursDate()).isEqualTo(UPDATED_RECOURS_DATE);
        assertThat(testDossier.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossier.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossier.isNumerise()).isEqualTo(UPDATED_NUMERISE);
        assertThat(testDossier.getAvocat()).isEqualTo(UPDATED_AVOCAT);
        assertThat(testDossier.getSecretaire()).isEqualTo(UPDATED_SECRETAIRE);
        assertThat(testDossier.getAssesseur()).isEqualTo(UPDATED_ASSESSEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Create the Dossier

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDossierMockMvc.perform(put("/api/dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isCreated());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        int databaseSizeBeforeDelete = dossierRepository.findAll().size();

        // Get the dossier
        restDossierMockMvc.perform(delete("/api/dossiers/{id}", dossier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dossier.class);
        Dossier dossier1 = new Dossier();
        dossier1.setId(1L);
        Dossier dossier2 = new Dossier();
        dossier2.setId(dossier1.getId());
        assertThat(dossier1).isEqualTo(dossier2);
        dossier2.setId(2L);
        assertThat(dossier1).isNotEqualTo(dossier2);
        dossier1.setId(null);
        assertThat(dossier1).isNotEqualTo(dossier2);
    }
}
