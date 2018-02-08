import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Avocat } from './avocat.model';
import { AvocatPopupService } from './avocat-popup.service';
import { AvocatService } from './avocat.service';

@Component({
    selector: 'jhi-avocat-delete-dialog',
    templateUrl: './avocat-delete-dialog.component.html'
})
export class AvocatDeleteDialogComponent {

    avocat: Avocat;

    constructor(
        private avocatService: AvocatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.avocatService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'avocatListModification',
                content: 'Deleted an avocat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-avocat-delete-popup',
    template: ''
})
export class AvocatDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private avocatPopupService: AvocatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.avocatPopupService
                .open(AvocatDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
