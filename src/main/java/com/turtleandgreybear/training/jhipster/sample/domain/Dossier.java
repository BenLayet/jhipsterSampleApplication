package com.turtleandgreybear.training.jhipster.sample.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.turtleandgreybear.training.jhipster.sample.domain.enumeration.DossierStatut;

/**
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recours_date")
    private LocalDate recoursDate;

    @Column(name = "numero")
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private DossierStatut statut;

    @Column(name = "numerise")
    private Boolean numerise;

    @Column(name = "avocat")
    private String avocat;

    @Column(name = "secretaire")
    private String secretaire;

    @Column(name = "assesseur")
    private String assesseur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRecoursDate() {
        return recoursDate;
    }

    public Dossier recoursDate(LocalDate recoursDate) {
        this.recoursDate = recoursDate;
        return this;
    }

    public void setRecoursDate(LocalDate recoursDate) {
        this.recoursDate = recoursDate;
    }

    public String getNumero() {
        return numero;
    }

    public Dossier numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public DossierStatut getStatut() {
        return statut;
    }

    public Dossier statut(DossierStatut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(DossierStatut statut) {
        this.statut = statut;
    }

    public Boolean isNumerise() {
        return numerise;
    }

    public Dossier numerise(Boolean numerise) {
        this.numerise = numerise;
        return this;
    }

    public void setNumerise(Boolean numerise) {
        this.numerise = numerise;
    }

    public String getAvocat() {
        return avocat;
    }

    public Dossier avocat(String avocat) {
        this.avocat = avocat;
        return this;
    }

    public void setAvocat(String avocat) {
        this.avocat = avocat;
    }

    public String getSecretaire() {
        return secretaire;
    }

    public Dossier secretaire(String secretaire) {
        this.secretaire = secretaire;
        return this;
    }

    public void setSecretaire(String secretaire) {
        this.secretaire = secretaire;
    }

    public String getAssesseur() {
        return assesseur;
    }

    public Dossier assesseur(String assesseur) {
        this.assesseur = assesseur;
        return this;
    }

    public void setAssesseur(String assesseur) {
        this.assesseur = assesseur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dossier dossier = (Dossier) o;
        if (dossier.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dossier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", recoursDate='" + getRecoursDate() + "'" +
            ", numero='" + getNumero() + "'" +
            ", statut='" + getStatut() + "'" +
            ", numerise='" + isNumerise() + "'" +
            ", avocat='" + getAvocat() + "'" +
            ", secretaire='" + getSecretaire() + "'" +
            ", assesseur='" + getAssesseur() + "'" +
            "}";
    }
}
