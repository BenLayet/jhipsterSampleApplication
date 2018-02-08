import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Avocat e2e test', () => {

    let navBarPage: NavBarPage;
    let avocatDialogPage: AvocatDialogPage;
    let avocatComponentsPage: AvocatComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Avocats', () => {
        navBarPage.goToEntity('avocat');
        avocatComponentsPage = new AvocatComponentsPage();
        expect(avocatComponentsPage.getTitle())
            .toMatch(/Avocats/);

    });

    it('should load create Avocat dialog', () => {
        avocatComponentsPage.clickOnCreateButton();
        avocatDialogPage = new AvocatDialogPage();
        expect(avocatDialogPage.getModalTitle())
            .toMatch(/Create or edit a Avocat/);
        avocatDialogPage.close();
    });

    it('should create and save Avocats', () => {
        avocatComponentsPage.clickOnCreateButton();
        avocatDialogPage.setAdresseInput('adresse');
        expect(avocatDialogPage.getAdresseInput()).toMatch('adresse');
        avocatDialogPage.setNomInput('nom');
        expect(avocatDialogPage.getNomInput()).toMatch('nom');
        avocatDialogPage.save();
        expect(avocatDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AvocatComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-avocat div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class AvocatDialogPage {
    modalTitle = element(by.css('h4#myAvocatLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    adresseInput = element(by.css('input#field_adresse'));
    nomInput = element(by.css('input#field_nom'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setAdresseInput = function(adresse) {
        this.adresseInput.sendKeys(adresse);
    };

    getAdresseInput = function() {
        return this.adresseInput.getAttribute('value');
    };

    setNomInput = function(nom) {
        this.nomInput.sendKeys(nom);
    };

    getNomInput = function() {
        return this.nomInput.getAttribute('value');
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
