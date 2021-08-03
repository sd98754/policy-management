import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConsumerBusinessRequest } from '../model/ConsumerBusinessRequest';
import { User } from '../model/user';
import { catchError, map, retry } from 'rxjs/operators';
import { ConsumerBusinessDetails } from '../model/ConsumerBusinessDetails';
import { BusinessPropertyRequest } from '../model/BusinessPropertyRequest';
import { BusinessPropertyDetails } from '../model/BusinessPropertyDetails';
import { Property } from '../model/Property';
import { CreatePolicyRequest } from '../model/CreatePolicyRequest';
import { IssuePolicyRequest } from '../model/IssuePolicyRequest';
import { PolicyDetailsResponse } from '../model/PolicyDetailsResponse';

@Injectable({
  providedIn: 'root'
})
export class Service {

  constructor(private http:HttpClient) { }

  authenticate(user: User){
    return this.http.post<any>("http://localhost:8400/auth/authenticate", user)
    .pipe(map(data =>{
      localStorage.setItem("token", data.token)
    }));
  }

  createConsumerBusiness(consumerBusinessRequest: ConsumerBusinessRequest){
    return this.http.post<any>("http://localhost:8200/consumer-api/createConsumerBusiness", consumerBusinessRequest, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
    .pipe(map(data =>{
      localStorage.setItem("createCBmessage", data.message)
    }));
  }

  viewConsumerBusiness(consumerId: number): Observable<ConsumerBusinessDetails>{
    return this.http.get<ConsumerBusinessDetails>("http://localhost:8200/consumer-api/viewConsumerBusiness/"+consumerId, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
  }

  updateConsumerBusiness(consumerBusinessDetails: ConsumerBusinessDetails){
    return this.http.post<any>("http://localhost:8200/consumer-api/updateConsumerBusiness", consumerBusinessDetails, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
    .pipe(map(data =>{
      localStorage.setItem("updateCBmessage", data.message)
    }));
  }

  createBusinessProperty(businessPropertyRequest: BusinessPropertyRequest){
    return this.http.post<any>("http://localhost:8200/consumer-api/createBusinessProperty", businessPropertyRequest, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
    .pipe(map(data =>{
      localStorage.setItem("createBPmessage", data.message)
    }))
  }

  viewBusinessProperty(consumerId: number, propertyId: number): Observable<Property>{
    return this.http.get<Property>("http://localhost:8200/consumer-api/viewConsumerProperty/"+consumerId+"/"+propertyId, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
  }

  updateBusinessProperty(businessPropertyDetails: BusinessPropertyDetails){
    return this.http.post<any>("http://localhost:8200/consumer-api/updateBusinessProperty", businessPropertyDetails, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
    .pipe(map(data => {
      localStorage.setItem("updateBPmessage", data.message)
    }))
  }

  getQuotes(businessValue: number, propertyValue: number, propertyType: string){
    return this.http.get<any>("http://localhost:8300/policy-api/getQuotes/"+businessValue+"/"+propertyValue+"/"+propertyType, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
    .pipe(map(data => {
      localStorage.setItem("viewQuotes", data.quotes)
    }))
  }

  createPolicy(createPolicyRequest: CreatePolicyRequest){
    return this.http.post<any>("http://localhost:8300/policy-api/createPolicy", createPolicyRequest, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
    .pipe(map(data =>{
      localStorage.setItem("createPolicy", data.message)
    }))
  }

  issuePolicy(issuePolicyRequest: IssuePolicyRequest){
    return this.http.post<any>("http://localhost:8300/policy-api/issuePolicy", issuePolicyRequest, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
    .pipe(map(data =>{
      localStorage.setItem("issuePolicy", data.message)
    }))
  }

  viewPolicy(consumerId: number, policyId: number): Observable<PolicyDetailsResponse>{
    return this.http.get<PolicyDetailsResponse>("http://localhost:8300/policy-api/viewPolicy/"+consumerId+"/"+policyId, {headers: new HttpHeaders().set('Authorization', "Bearer " + localStorage.getItem("token"))})
  }

}