import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import * as zipkin from 'zipkin';
import * as zipkinTransportHttp from 'zipkin-transport-http';

@Injectable()
export class ZipkinHttpInterceptor implements HttpInterceptor {

    private readonly tracer: zipkin.Tracer;
    private readonly instrumentation: zipkin.Instrumentation.HttpClient;

    constructor() {
        const localServiceName = 'store-kiosk';
        const remoteServiceName = 'store-operations';

        this.tracer = new zipkin.Tracer({
            ctxImpl: new zipkin.ExplicitContext(),
            recorder: new zipkin.BatchRecorder({
                logger: new zipkinTransportHttp.HttpLogger({
                    endpoint: 'wavefront/api/v2/spans',
                    jsonEncoder: zipkin.jsonEncoder.JSON_V2
                })
            }),
            localServiceName: localServiceName,
            traceId128Bit: true,
            defaultTags: { application: 'vere-copia', service: 'store-kiosk' },
        });
        this.instrumentation = new zipkin.Instrumentation.HttpClient(
            { tracer: this.tracer, serviceName: localServiceName, remoteServiceName: remoteServiceName }
        );

    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return new Observable(observer => {
            this.tracer.scoped(() => {
                const options = this.instrumentation.recordRequest({ url: request.url, headers: {} }, request.url, request.method || 'GET');
                request = request.clone({
                    setHeaders: options.headers as any
                });

                const traceId = this.tracer.id
                next.handle(request).pipe(tap((event: HttpEvent<any>) => {
                    if (event instanceof HttpResponse) {
                        this.tracer.scoped(() => {
                            if (event.ok) {
                                this.instrumentation.recordResponse(traceId, event.status.toString())
                            } else {
                                this.instrumentation.recordError(traceId, new Error('status ' + event.status))
                            }
                        })
                    }
                })).subscribe(event => observer.next(event));
            });
        });
    }
}
