import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DossierComponent } from './dossier.component';
import { DossierDetailComponent } from './dossier-detail.component';
import { DossierPopupComponent } from './dossier-dialog.component';
import { DossierDeletePopupComponent } from './dossier-delete-dialog.component';

export const dossierRoute: Routes = [
    {
        path: 'dossier',
        component: DossierComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dossiers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dossier/:id',
        component: DossierDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dossiers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dossierPopupRoute: Routes = [
    {
        path: 'dossier-new',
        component: DossierPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dossiers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dossier/:id/edit',
        component: DossierPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dossiers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dossier/:id/delete',
        component: DossierDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dossiers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
