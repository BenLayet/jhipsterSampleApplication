import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AvocatComponent } from './avocat.component';
import { AvocatDetailComponent } from './avocat-detail.component';
import { AvocatPopupComponent } from './avocat-dialog.component';
import { AvocatDeletePopupComponent } from './avocat-delete-dialog.component';

export const avocatRoute: Routes = [
    {
        path: 'avocat',
        component: AvocatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avocats'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'avocat/:id',
        component: AvocatDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avocats'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const avocatPopupRoute: Routes = [
    {
        path: 'avocat-new',
        component: AvocatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avocats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'avocat/:id/edit',
        component: AvocatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avocats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'avocat/:id/delete',
        component: AvocatDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avocats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
