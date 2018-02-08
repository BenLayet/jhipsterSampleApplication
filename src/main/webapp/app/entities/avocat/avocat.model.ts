import { BaseEntity } from './../../shared';

export class Avocat implements BaseEntity {
    constructor(
        public id?: number,
        public adresse?: string,
        public nom?: string,
    ) {
    }
}
