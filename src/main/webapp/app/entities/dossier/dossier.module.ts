import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    DossierService,
    DossierPopupService,
    DossierComponent,
    DossierDetailComponent,
    DossierDialogComponent,
    DossierPopupComponent,
    DossierDeletePopupComponent,
    DossierDeleteDialogComponent,
    dossierRoute,
    dossierPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dossierRoute,
    ...dossierPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DossierComponent,
        DossierDetailComponent,
        DossierDialogComponent,
        DossierDeleteDialogComponent,
        DossierPopupComponent,
        DossierDeletePopupComponent,
    ],
    entryComponents: [
        DossierComponent,
        DossierDialogComponent,
        DossierPopupComponent,
        DossierDeleteDialogComponent,
        DossierDeletePopupComponent,
    ],
    providers: [
        DossierService,
        DossierPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDossierModule {}
