/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AvocatDetailComponent } from '../../../../../../main/webapp/app/entities/avocat/avocat-detail.component';
import { AvocatService } from '../../../../../../main/webapp/app/entities/avocat/avocat.service';
import { Avocat } from '../../../../../../main/webapp/app/entities/avocat/avocat.model';

describe('Component Tests', () => {

    describe('Avocat Management Detail Component', () => {
        let comp: AvocatDetailComponent;
        let fixture: ComponentFixture<AvocatDetailComponent>;
        let service: AvocatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AvocatDetailComponent],
                providers: [
                    AvocatService
                ]
            })
            .overrideTemplate(AvocatDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AvocatDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvocatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Avocat(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.avocat).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
