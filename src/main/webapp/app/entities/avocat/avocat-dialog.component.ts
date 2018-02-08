import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Avocat } from './avocat.model';
import { AvocatPopupService } from './avocat-popup.service';
import { AvocatService } from './avocat.service';

@Component({
    selector: 'jhi-avocat-dialog',
    templateUrl: './avocat-dialog.component.html'
})
export class AvocatDialogComponent implements OnInit {

    avocat: Avocat;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private avocatService: AvocatService,
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
        if (this.avocat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.avocatService.update(this.avocat));
        } else {
            this.subscribeToSaveResponse(
                this.avocatService.create(this.avocat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Avocat>>) {
        result.subscribe((res: HttpResponse<Avocat>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Avocat) {
        this.eventManager.broadcast({ name: 'avocatListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-avocat-popup',
    template: ''
})
export class AvocatPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private avocatPopupService: AvocatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.avocatPopupService
                    .open(AvocatDialogComponent as Component, params['id']);
            } else {
                this.avocatPopupService
                    .open(AvocatDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
