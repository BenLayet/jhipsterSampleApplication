import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    AvocatService,
    AvocatPopupService,
    AvocatComponent,
    AvocatDetailComponent,
    AvocatDialogComponent,
    AvocatPopupComponent,
    AvocatDeletePopupComponent,
    AvocatDeleteDialogComponent,
    avocatRoute,
    avocatPopupRoute,
} from './';

const ENTITY_STATES = [
    ...avocatRoute,
    ...avocatPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AvocatComponent,
        AvocatDetailComponent,
        AvocatDialogComponent,
        AvocatDeleteDialogComponent,
        AvocatPopupComponent,
        AvocatDeletePopupComponent,
    ],
    entryComponents: [
        AvocatComponent,
        AvocatDialogComponent,
        AvocatPopupComponent,
        AvocatDeleteDialogComponent,
        AvocatDeletePopupComponent,
    ],
    providers: [
        AvocatService,
        AvocatPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationAvocatModule {}
