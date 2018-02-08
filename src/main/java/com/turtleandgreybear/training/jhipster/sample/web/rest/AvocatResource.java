package com.turtleandgreybear.training.jhipster.sample.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.turtleandgreybear.training.jhipster.sample.domain.Avocat;

import com.turtleandgreybear.training.jhipster.sample.repository.AvocatRepository;
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
 * REST controller for managing Avocat.
 */
@RestController
@RequestMapping("/api")
public class AvocatResource {

    private final Logger log = LoggerFactory.getLogger(AvocatResource.class);

    private static final String ENTITY_NAME = "avocat";

    private final AvocatRepository avocatRepository;

    public AvocatResource(AvocatRepository avocatRepository) {
        this.avocatRepository = avocatRepository;
    }

    /**
     * POST  /avocats : Create a new avocat.
     *
     * @param avocat the avocat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avocat, or with status 400 (Bad Request) if the avocat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avocats")
    @Timed
    public ResponseEntity<Avocat> createAvocat(@RequestBody Avocat avocat) throws URISyntaxException {
        log.debug("REST request to save Avocat : {}", avocat);
        if (avocat.getId() != null) {
            throw new BadRequestAlertException("A new avocat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Avocat result = avocatRepository.save(avocat);
        return ResponseEntity.created(new URI("/api/avocats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avocats : Updates an existing avocat.
     *
     * @param avocat the avocat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avocat,
     * or with status 400 (Bad Request) if the avocat is not valid,
     * or with status 500 (Internal Server Error) if the avocat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avocats")
    @Timed
    public ResponseEntity<Avocat> updateAvocat(@RequestBody Avocat avocat) throws URISyntaxException {
        log.debug("REST request to update Avocat : {}", avocat);
        if (avocat.getId() == null) {
            return createAvocat(avocat);
        }
        Avocat result = avocatRepository.save(avocat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, avocat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avocats : get all the avocats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of avocats in body
     */
    @GetMapping("/avocats")
    @Timed
    public List<Avocat> getAllAvocats() {
        log.debug("REST request to get all Avocats");
        return avocatRepository.findAll();
        }

    /**
     * GET  /avocats/:id : get the "id" avocat.
     *
     * @param id the id of the avocat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avocat, or with status 404 (Not Found)
     */
    @GetMapping("/avocats/{id}")
    @Timed
    public ResponseEntity<Avocat> getAvocat(@PathVariable Long id) {
        log.debug("REST request to get Avocat : {}", id);
        Avocat avocat = avocatRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(avocat));
    }

    /**
     * DELETE  /avocats/:id : delete the "id" avocat.
     *
     * @param id the id of the avocat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avocats/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvocat(@PathVariable Long id) {
        log.debug("REST request to delete Avocat : {}", id);
        avocatRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
