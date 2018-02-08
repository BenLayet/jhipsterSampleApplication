package com.turtleandgreybear.training.jhipster.sample.repository;

import com.turtleandgreybear.training.jhipster.sample.domain.Dossier;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dossier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {

}
