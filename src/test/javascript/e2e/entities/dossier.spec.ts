import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Dossier e2e test', () => {

    let navBarPage: NavBarPage;
    let dossierDialogPage: DossierDialogPage;
    let dossierComponentsPage: DossierComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Dossiers', () => {
        navBarPage.goToEntity('dossier');
        dossierComponentsPage = new DossierComponentsPage();
        expect(dossierComponentsPage.getTitle())
            .toMatch(/Dossiers/);

    });

    it('should load create Dossier dialog', () => {
        dossierComponentsPage.clickOnCreateButton();
        dossierDialogPage = new DossierDialogPage();
        expect(dossierDialogPage.getModalTitle())
            .toMatch(/Create or edit a Dossier/);
        dossierDialogPage.close();
    });

    it('should create and save Dossiers', () => {
        dossierComponentsPage.clickOnCreateButton();
        dossierDialogPage.setRecoursDateInput('2000-12-31');
        expect(dossierDialogPage.getRecoursDateInput()).toMatch('2000-12-31');
        dossierDialogPage.setNumeroInput('numero');
        expect(dossierDialogPage.getNumeroInput()).toMatch('numero');
        dossierDialogPage.statutSelectLastOption();
        dossierDialogPage.getNumeriseInput().isSelected().then((selected) => {
            if (selected) {
                dossierDialogPage.getNumeriseInput().click();
                expect(dossierDialogPage.getNumeriseInput().isSelected()).toBeFalsy();
            } else {
                dossierDialogPage.getNumeriseInput().click();
                expect(dossierDialogPage.getNumeriseInput().isSelected()).toBeTruthy();
            }
        });
        dossierDialogPage.setAvocatInput('avocat');
        expect(dossierDialogPage.getAvocatInput()).toMatch('avocat');
        dossierDialogPage.setSecretaireInput('secretaire');
        expect(dossierDialogPage.getSecretaireInput()).toMatch('secretaire');
        dossierDialogPage.setAssesseurInput('assesseur');
        expect(dossierDialogPage.getAssesseurInput()).toMatch('assesseur');
        dossierDialogPage.save();
        expect(dossierDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DossierComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-dossier div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class DossierDialogPage {
    modalTitle = element(by.css('h4#myDossierLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    recoursDateInput = element(by.css('input#field_recoursDate'));
    numeroInput = element(by.css('input#field_numero'));
    statutSelect = element(by.css('select#field_statut'));
    numeriseInput = element(by.css('input#field_numerise'));
    avocatInput = element(by.css('input#field_avocat'));
    secretaireInput = element(by.css('input#field_secretaire'));
    assesseurInput = element(by.css('input#field_assesseur'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setRecoursDateInput = function(recoursDate) {
        this.recoursDateInput.sendKeys(recoursDate);
    };

    getRecoursDateInput = function() {
        return this.recoursDateInput.getAttribute('value');
    };

    setNumeroInput = function(numero) {
        this.numeroInput.sendKeys(numero);
    };

    getNumeroInput = function() {
        return this.numeroInput.getAttribute('value');
    };

    setStatutSelect = function(statut) {
        this.statutSelect.sendKeys(statut);
    };

    getStatutSelect = function() {
        return this.statutSelect.element(by.css('option:checked')).getText();
    };

    statutSelectLastOption = function() {
        this.statutSelect.all(by.tagName('option')).last().click();
    };
    getNumeriseInput = function() {
        return this.numeriseInput;
    };
    setAvocatInput = function(avocat) {
        this.avocatInput.sendKeys(avocat);
    };

    getAvocatInput = function() {
        return this.avocatInput.getAttribute('value');
    };

    setSecretaireInput = function(secretaire) {
        this.secretaireInput.sendKeys(secretaire);
    };

    getSecretaireInput = function() {
        return this.secretaireInput.getAttribute('value');
    };

    setAssesseurInput = function(assesseur) {
        this.assesseurInput.sendKeys(assesseur);
    };

    getAssesseurInput = function() {
        return this.assesseurInput.getAttribute('value');
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
