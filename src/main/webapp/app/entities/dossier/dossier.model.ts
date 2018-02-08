import { BaseEntity } from './../../shared';

export const enum DossierStatut {
    'RECU',
    'COMPLET'
}

export class Dossier implements BaseEntity {
    constructor(
        public id?: number,
        public recoursDate?: any,
        public numero?: string,
        public statut?: DossierStatut,
        public numerise?: boolean,
        public avocat?: string,
        public secretaire?: string,
        public assesseur?: string,
    ) {
        this.numerise = false;
    }
}
