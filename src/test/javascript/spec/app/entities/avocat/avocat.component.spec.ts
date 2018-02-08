/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AvocatComponent } from '../../../../../../main/webapp/app/entities/avocat/avocat.component';
import { AvocatService } from '../../../../../../main/webapp/app/entities/avocat/avocat.service';
import { Avocat } from '../../../../../../main/webapp/app/entities/avocat/avocat.model';

describe('Component Tests', () => {

    describe('Avocat Management Component', () => {
        let comp: AvocatComponent;
        let fixture: ComponentFixture<AvocatComponent>;
        let service: AvocatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AvocatComponent],
                providers: [
                    AvocatService
                ]
            })
            .overrideTemplate(AvocatComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AvocatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvocatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Avocat(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.avocats[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
