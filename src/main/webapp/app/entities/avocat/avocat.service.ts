import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Avocat } from './avocat.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Avocat>;

@Injectable()
export class AvocatService {

    private resourceUrl =  SERVER_API_URL + 'api/avocats';

    constructor(private http: HttpClient) { }

    create(avocat: Avocat): Observable<EntityResponseType> {
        const copy = this.convert(avocat);
        return this.http.post<Avocat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(avocat: Avocat): Observable<EntityResponseType> {
        const copy = this.convert(avocat);
        return this.http.put<Avocat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Avocat>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Avocat[]>> {
        const options = createRequestOption(req);
        return this.http.get<Avocat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Avocat[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Avocat = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Avocat[]>): HttpResponse<Avocat[]> {
        const jsonResponse: Avocat[] = res.body;
        const body: Avocat[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Avocat.
     */
    private convertItemFromServer(avocat: Avocat): Avocat {
        const copy: Avocat = Object.assign({}, avocat);
        return copy;
    }

    /**
     * Convert a Avocat to a JSON which can be sent to the server.
     */
    private convert(avocat: Avocat): Avocat {
        const copy: Avocat = Object.assign({}, avocat);
        return copy;
    }
}
