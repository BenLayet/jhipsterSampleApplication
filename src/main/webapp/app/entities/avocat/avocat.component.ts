import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Avocat } from './avocat.model';
import { AvocatService } from './avocat.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-avocat',
    templateUrl: './avocat.component.html'
})
export class AvocatComponent implements OnInit, OnDestroy {
avocats: Avocat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private avocatService: AvocatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.avocatService.query().subscribe(
            (res: HttpResponse<Avocat[]>) => {
                this.avocats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAvocats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Avocat) {
        return item.id;
    }
    registerChangeInAvocats() {
        this.eventSubscriber = this.eventManager.subscribe('avocatListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
