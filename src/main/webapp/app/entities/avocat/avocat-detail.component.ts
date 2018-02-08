import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Avocat } from './avocat.model';
import { AvocatService } from './avocat.service';

@Component({
    selector: 'jhi-avocat-detail',
    templateUrl: './avocat-detail.component.html'
})
export class AvocatDetailComponent implements OnInit, OnDestroy {

    avocat: Avocat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private avocatService: AvocatService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAvocats();
    }

    load(id) {
        this.avocatService.find(id)
            .subscribe((avocatResponse: HttpResponse<Avocat>) => {
                this.avocat = avocatResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAvocats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'avocatListModification',
            (response) => this.load(this.avocat.id)
        );
    }
}
