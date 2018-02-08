import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Dossier } from './dossier.model';
import { DossierService } from './dossier.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-dossier',
    templateUrl: './dossier.component.html'
})
export class DossierComponent implements OnInit, OnDestroy {
dossiers: Dossier[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dossierService: DossierService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.dossierService.query().subscribe(
            (res: HttpResponse<Dossier[]>) => {
                this.dossiers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDossiers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Dossier) {
        return item.id;
    }
    registerChangeInDossiers() {
        this.eventSubscriber = this.eventManager.subscribe('dossierListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
