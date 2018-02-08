package com.turtleandgreybear.training.jhipster.sample.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.turtleandgreybear.training.jhipster.sample.domain.Dossier;

import com.turtleandgreybear.training.jhipster.sample.repository.DossierRepository;
import com.turtleandgreybear.training.jhipster.sample.web.rest.errors.BadRequestAlertException;
import com.turtleandgreybear.training.jhipster.sample.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dossier.
 */
@RestController
@RequestMapping("/api")
public class DossierResource {

    private final Logger log = LoggerFactory.getLogger(DossierResource.class);

    private static final String ENTITY_NAME = "dossier";

    private final DossierRepository dossierRepository;

    public DossierResource(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    /**
     * POST  /dossiers : Create a new dossier.
     *
     * @param dossier the dossier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dossier, or with status 400 (Bad Request) if the dossier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dossiers")
    @Timed
    public ResponseEntity<Dossier> createDossier(@RequestBody Dossier dossier) throws URISyntaxException {
        log.debug("REST request to save Dossier : {}", dossier);
        if (dossier.getId() != null) {
            throw new BadRequestAlertException("A new dossier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dossier result = dossierRepository.save(dossier);
        return ResponseEntity.created(new URI("/api/dossiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dossiers : Updates an existing dossier.
     *
     * @param dossier the dossier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dossier,
     * or with status 400 (Bad Request) if the dossier is not valid,
     * or with status 500 (Internal Server Error) if the dossier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dossiers")
    @Timed
    public ResponseEntity<Dossier> updateDossier(@RequestBody Dossier dossier) throws URISyntaxException {
        log.debug("REST request to update Dossier : {}", dossier);
        if (dossier.getId() == null) {
            return createDossier(dossier);
        }
        Dossier result = dossierRepository.save(dossier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dossier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dossiers : get all the dossiers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dossiers in body
     */
    @GetMapping("/dossiers")
    @Timed
    public List<Dossier> getAllDossiers() {
        log.debug("REST request to get all Dossiers");
        return dossierRepository.findAll();
        }

    /**
     * GET  /dossiers/:id : get the "id" dossier.
     *
     * @param id the id of the dossier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dossier, or with status 404 (Not Found)
     */
    @GetMapping("/dossiers/{id}")
    @Timed
    public ResponseEntity<Dossier> getDossier(@PathVariable Long id) {
        log.debug("REST request to get Dossier : {}", id);
        Dossier dossier = dossierRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dossier));
    }

    /**
     * DELETE  /dossiers/:id : delete the "id" dossier.
     *
     * @param id the id of the dossier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dossiers/{id}")
    @Timed
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        log.debug("REST request to delete Dossier : {}", id);
        dossierRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
