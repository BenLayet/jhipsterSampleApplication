import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Dossier } from './dossier.model';
import { DossierPopupService } from './dossier-popup.service';
import { DossierService } from './dossier.service';

@Component({
    selector: 'jhi-dossier-dialog',
    templateUrl: './dossier-dialog.component.html'
})
export class DossierDialogComponent implements OnInit {

    dossier: Dossier;
    isSaving: boolean;
    recoursDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dossierService: DossierService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dossier.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dossierService.update(this.dossier));
        } else {
            this.subscribeToSaveResponse(
                this.dossierService.create(this.dossier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Dossier>>) {
        result.subscribe((res: HttpResponse<Dossier>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Dossier) {
        this.eventManager.broadcast({ name: 'dossierListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-dossier-popup',
    template: ''
})
export class DossierPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dossierPopupService: DossierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dossierPopupService
                    .open(DossierDialogComponent as Component, params['id']);
            } else {
                this.dossierPopupService
                    .open(DossierDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
