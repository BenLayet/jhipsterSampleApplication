package com.turtleandgreybear.training.jhipster.sample.repository;

import com.turtleandgreybear.training.jhipster.sample.domain.Avocat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Avocat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvocatRepository extends JpaRepository<Avocat, Long> {

}
