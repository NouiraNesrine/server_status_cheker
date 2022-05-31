import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, tap, throwError} from "rxjs";
import {CustomResponse} from "../interface/custom-response";
import {Server} from "../interface/server";
import {Status} from "../enum/status.enum";

@Injectable({  providedIn: 'root'})
export class ServerService {

  constructor(private http:HttpClient) { }

  private readonly apiURL='any';
//procudarale approche
 /* getServers(): Observable<CustomResponse>{
    return this.http.get<CustomResponse>('http://localhost:8080/server/list');
  }
  */

  servers$ = <Observable<CustomResponse>>this.http.get<CustomResponse>(`${this.apiURL}/server/list`)
    .pipe(
      tap(console.log),
      catchError(this.errorHandler)
    );

  save$ = (server: Server) => <Observable<CustomResponse>>this.http.post<CustomResponse>(`${this.apiURL}/server/save`,server)
    .pipe(
      tap(console.log),
      catchError(this.errorHandler)
    );

  ping$ = (ipAddress: string) => <Observable<CustomResponse>>this.http.get<CustomResponse>(`${this.apiURL}/server/ping/${ipAddress}`)
    .pipe(
      tap(console.log),
      catchError(this.errorHandler)
    );

  filter$ = (status: Status, response:CustomResponse) => <Observable<CustomResponse>> new Observable<CustomResponse>(
    subscriber => {
      console.log(response);
      subscriber.next(
        status=== Status.ALL ? {...response,devMessage:`Servers filtered by ${status} status`} :
          { ...response,
            devMessage: response.data.servers?.filter(server=> server.status=== status).length > 0 ? `Servers filtered by ${status} status` : `No servers filtered by ${status} status`,
            data : {servers : response.data.servers.filter(server=> server.status===status)}
          }
      );
      subscriber.complete();
    }
  )
    .pipe(
      tap(console.log),
      catchError(this.errorHandler)
    );

  delete$ = (id: number) => <Observable<CustomResponse>>this.http.delete<CustomResponse>(`${this.apiURL}/server/delete/${id}`)
    .pipe(
      tap(console.log),
      catchError(this.errorHandler)
    );

  private errorHandler(error:HttpErrorResponse):Observable<never>{
    console.log(error)
    return throwError (`error !! !-> ${error.status}`);
  };

}
